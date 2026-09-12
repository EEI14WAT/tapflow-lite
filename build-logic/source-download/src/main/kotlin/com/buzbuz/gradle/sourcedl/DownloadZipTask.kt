/*
* Copyright (C) 2024 Kevin Buzeau
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*/
package com.buzbuz.gradle.sourcedl

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.StandardCopyOption.ATOMIC_MOVE
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.security.MessageDigest
import java.util.zip.ZipFile

abstract class DownloadZipTask : DefaultTask() {

    @get:Input abstract val downloadUrl: Property<String>
    @get:Input abstract val expectedSha256: Property<String>
    @get:Internal abstract val outputFile: Property<File>

    init {
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun download() {
        val archive = outputFile.get()
        if (isVerifiedArchive(archive)) {
            logger.lifecycle("Reusing verified source archive: ${archive.path}")
            return
        }
        if (project.gradle.startParameter.isOffline) {
            throw GradleException("Offline mode requires a verified archive at ${archive.path}. See documentation/RELEASE_SIGNING.md for the NCNN manual-cache procedure.")
        }

        val url = URI(downloadUrl.get()).toURL()
        require(url.protocol == "https") { "Source archive URL must use HTTPS: $url" }
        archive.parentFile?.mkdirs()
        val partial = File(archive.parentFile, "${archive.name}.part")
        Files.deleteIfExists(partial.toPath())
        Files.deleteIfExists(archive.toPath())

        try {
            logger.lifecycle("Downloading source archive from $url")
            val connection = (url.openConnection() as HttpURLConnection).apply {
                connectTimeout = CONNECT_TIMEOUT_MS
                readTimeout = READ_TIMEOUT_MS
                instanceFollowRedirects = true
                requestMethod = "GET"
            }
            try {
                val status = connection.responseCode
                if (status !in 200..299) throw GradleException("Source archive download failed with HTTP $status: $url")
                if (connection.contentType?.lowercase()?.contains("text/html") == true) {
                    throw GradleException("Source archive download returned HTML instead of a ZIP: $url")
                }
                copyToPartial(connection, partial)
            } finally {
                connection.disconnect()
            }
            if (!isVerifiedArchive(partial)) throw GradleException("Downloaded archive failed ZIP or SHA-256 verification: $url")
            try {
                Files.move(partial.toPath(), archive.toPath(), ATOMIC_MOVE, REPLACE_EXISTING)
            } catch (_: AtomicMoveNotSupportedException) {
                Files.move(partial.toPath(), archive.toPath(), REPLACE_EXISTING)
            }
            logger.lifecycle("Verified source archive: ${archive.path}")
        } finally {
            Files.deleteIfExists(partial.toPath())
        }
    }

    private fun copyToPartial(connection: HttpURLConnection, partial: File) {
        var copied = 0L
        var nextProgress = PROGRESS_INTERVAL_BYTES
        connection.inputStream.use { input ->
            FileOutputStream(partial).buffered().use { output ->
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                while (true) {
                    val count = input.read(buffer)
                    if (count < 0) break
                    output.write(buffer, 0, count)
                    copied += count
                    if (copied >= nextProgress) {
                        logger.lifecycle("Downloaded ${copied / 1_048_576} MiB of source archive")
                        nextProgress += PROGRESS_INTERVAL_BYTES
                    }
                }
            }
        }
    }

    private fun isVerifiedArchive(file: File): Boolean {
        if (!file.isFile || file.length() < 4L) return false
        val expected = expectedSha256.orNull?.lowercase().orEmpty()
        if (expected.isNotEmpty() && sha256(file) != expected) return false
        return try { ZipFile(file).use { it.entries().hasMoreElements() } } catch (_: Exception) { false }
    }

    private fun sha256(file: File): String = MessageDigest.getInstance("SHA-256").digest(file.readBytes()).joinToString("") { "%02x".format(it) }

    private companion object {
        const val CONNECT_TIMEOUT_MS = 30_000
        const val READ_TIMEOUT_MS = 30_000
        const val PROGRESS_INTERVAL_BYTES = 5L * 1_048_576
    }
}
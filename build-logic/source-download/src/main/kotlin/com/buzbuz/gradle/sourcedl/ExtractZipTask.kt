/*
* Copyright (C) 2024 Kevin Buzeau
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.buzbuz.gradle.sourcedl

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

abstract class ExtractZipTask : DefaultTask() {

    @get:Input
    abstract val sourceVersion: Property<String>
    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val inputZipFile: Property<File>
    @get:Input
    abstract val fileFiltersRegexes: ListProperty<String>
    @get:Input
    abstract val foldersMapping: MapProperty<String, String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun extract() {
        val outputDir = outputDirectory.get()
        val archive = inputZipFile.get()
        val archiveSha256 = MessageDigest.getInstance("SHA-256").digest(archive.readBytes()).joinToString("") { "%02x".format(it) }
        val marker = "sourceVersion=${sourceVersion.get()}\narchiveSha256=$archiveSha256\n"

        val sourceCodeVersionFile = project.file("${outputDir.asFile.path}/version.txt")
        if (sourceCodeVersionFile.isFile && sourceCodeVersionFile.readText() == marker) {
            logger.lifecycle("Reusing extracted sources: ${outputDir.asFile}")
            return
        }
        if (outputDir.asFile.exists()) project.delete(outputDir)
        project.mkdir(outputDir)

        val filters = fileFiltersRegexes.get().map { Regex(it) }
        val mapping = foldersMapping.get()

        ZipFile(archive).use { zipFile ->
            val entries = zipFile.entries().asSequence().toList()
            val commonRootDirectory = entries.map { it.name.substringBefore("/") }.distinct().singleOrNull()
            entries.forEach { entry ->
                zipFile.copyZipEntryIfNeeded(entry, outputDir.asFile, filters, mapping, commonRootDirectory)
            }
        }

        project.file(sourceCodeVersionFile.toPath())
            .writeText(marker)
    }

    private fun ZipFile.copyZipEntryIfNeeded(
        entry: ZipEntry,
        outputDir: File,
        filters: List<Regex>,
        mapping: Map<String, String>,
        commonRootDirectory: String?,
    ) {
        if (entry.isDirectory) return
        var entryPath = entry.name
        if (commonRootDirectory != null) entryPath = entryPath.removePrefix("$commonRootDirectory/")
        if (entryPath.isEmpty()) return

        // Filters unwanted files
        if (filters.isNotEmpty() && !entryPath.matchFilters(filters)) return

        // Map to new folder if needed
        entryPath.getMapping(mapping)?.let { (toReplace, replacement) ->
            entryPath = entryPath.replace(toReplace, replacement)
        }

        File(outputDir, entryPath).let { outputFile ->
            require(outputFile.canonicalFile.toPath().startsWith(outputDir.canonicalFile.toPath())) {
                "ZIP entry escapes extraction directory: ${entry.name}"
            }
            project.mkdir(outputFile.parentFile.toPath())
            getInputStream(entry).use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    private fun String.matchFilters(filters: List<Regex>): Boolean =
        filters.find { filter -> matches(filter) } != null

    private fun String.getMapping(mapping: Map<String, String>): Pair<String, String>? {
        mapping.forEach { (toReplace, replacement) ->
            if (startsWith(toReplace)) return toReplace to replacement
        }

        return null
    }
}
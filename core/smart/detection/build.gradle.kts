@file:Suppress("UnstableApiUsage")

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

import com.buzbuz.gradle.convention.extensions.fDroid

plugins {
    alias(libs.plugins.buzbuz.androidLibrary)
    alias(libs.plugins.buzbuz.androidLocalTest)
    alias(libs.plugins.buzbuz.flavour)
    alias(libs.plugins.buzbuz.sourceDownload)
}

sourceDownload {
    projects {
        register("openCv") {
            projectAccount = "opencv"
            projectName = "opencv"
            projectVersion = libs.versions.openCv.get()
            downloadUrl = "https://github.com/opencv/opencv/archive/refs/tags/4.12.0.zip"
            archiveFileName = "opencv-4.12.0.zip"
            expectedSha256 = "fa3faf7581f1fa943c9e670cf57dd6ba1c5b4178f363a188a2c8bff1eb28b7e4"

            unzipPath = File("src/release/opencv")
            requiredForTask = "configureCMakeRelease"
        }

        register("ncnn") {
            projectAccount = "Tencent"
            projectName = "ncnn"
            projectVersion = libs.versions.ncnn.get()
            downloadUrl = "https://github.com/Tencent/ncnn/releases/download/20260113/ncnn-20260113-full-source.zip"
            archiveFileName = "ncnn-20260113-full-source.zip"
            expectedSha256 = "53696039ee8ba5c8db6446bdf12a576b8d7f7b0c33bb6749f94688bddf5a3d5c"

            unzipPath = File("src/release/ncnn")
            requiredForTask = "configureCMakeRelease"
        }
    }
}

android {
    namespace = "com.buzbuz.smartautoclicker.core.detection"

    androidResources {
        noCompress += listOf("bin", "param")
    }

    defaultConfig {
        externalNativeBuild {
            cmake {

            }
        }
    }

    buildTypes {
        debug {
            externalNativeBuild {
                cmake {
                    arguments.addAll(
                        listOf("-DCMAKE_BUILD_TYPE=Debug")
                    )
                }
            }
        }

        release {
            ndk {
                debugSymbolLevel = "NONE"
            }
            externalNativeBuild {
                cmake {
                    arguments.addAll(
                        listOf(
                            "-DANDROID_SDK_ROOT=${project.androidComponents.sdkComponents.sdkDirectory}",
                            "-DCMAKE_BUILD_TYPE=Release",
                            "-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON",
                            "-DOPENCV_ENABLE_NONFREE=OFF",
                            "-DBUILD_opencv_ittnotify=OFF",
                            "-DBUILD_ITT=OFF",
                            "-DCV_DISABLE_OPTIMIZATION=ON",
                            "-DWITH_CUDA=OFF",
                            "-DWITH_OPENCL=OFF",
                            "-DWITH_OPENCLAMDFFT=OFF",
                            "-DWITH_OPENCLAMDBLAS=OFF",
                            "-DWITH_VA_INTEL=OFF",
                            "-DENABLE_SSE=OFF",
                            "-DENABLE_SSE2=OFF",
                            "-DBUILD_TESTING=OFF",
                            "-DBUILD_PERF_TESTS=OFF",
                            "-DBUILD_TESTS=OFF",
                            "-DBUILD_EXAMPLES=OFF",
                            "-DBUILD_DOCS=OFF",
                            "-DBUILD_opencv_apps=OFF",
                            "-DWITH_1394=OFF",
                            "-DWITH_ARITH_DEC=OFF",
                            "-DWITH_ARITH_ENC=OFF",
                            "-DWITH_CUBLAS=OFF",
                            "-DWITH_CUFFT=OFF",
                            "-DWITH_FFMPEG=OFF",
                            "-DWITH_GDAL=OFF",
                            "-DWITH_GSTREAMER=OFF",
                            "-DWITH_GTK=OFF",
                            "-DWITH_HALIDE=OFF",
                            "-DWITH_JASPER=OFF",
                            "-DWITH_NVCUVID=OFF",
                            "-DWITH_OPENEXR=OFF",
                            "-DWITH_PROTOBUF=OFF",
                            "-DWITH_PTHREADS_PF=OFF",
                            "-DWITH_QUIRC=OFF",
                            "-DWITH_V4L=OFF",
                            "-DWITH_WEBP=OFF",
                            "-DBUILD_LIST=core,imgproc",
                            "-DBUILD_JAVA=OFF",
                            "-DBUILD_ANDROID_EXAMPLES=OFF",
                            "-DBUILD_ANDROID_PROJECTS=OFF",
                            "-DBUILD_SHARED_LIBS=ON",
                            "-DNCNN_SHARED_LIB=ON",
                            "-DNCNN_BUILD_TOOLS=OFF",
                            "-DNCNN_BUILD_EXAMPLES=OFF",
                            "-DNCNN_BUILD_BENCHMARK=OFF",
                            "-DNCNN_BUILD_TESTS=OFF",
                            "-DNCNN_VULKAN=OFF",
                            "-DNCNN_OPENMP=OFF",
                            "-DNCNN_RUNTIME_CPU=ON",
                            "-DNCNN_DISABLE_RTTI=OFF",
                            "-DNCNN_DISABLE_EXCEPTION=OFF",
                            "-DNCNN_BF16=OFF",
                            "-DNCNN_FP16=OFF",
                            "-DNCNN_INT8=OFF",
                        )
                    )
                }
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = File("src/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    productFlavors {
        fDroid {
            externalNativeBuild.cmake.arguments.addAll(
                listOf("-DWITH_BUILD_ID=OFF")
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.annotation)
    implementation(project(":core:common:base"))
}

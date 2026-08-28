package com.louis993546.metro.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MetroAndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("metro.detekt")
            }

            val targetSdkVersion = libs.findVersion("targetSdk").get().requiredVersion.toInt()

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    targetSdk = targetSdkVersion

                    val runNumber = providers.environmentVariable("GITHUB_RUN_NUMBER")
                        .map { it.toInt() }
                        .getOrElse(1)
                    versionCode = runNumber
                    versionName = "0.$runNumber.0"

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                signingConfigs {
                    getByName("debug") {
                        storeFile = rootProject.file("debug.keystore")
                    }
                }

                buildTypes {
                    getByName("debug") {
                        applicationIdSuffix = ".debug"
                    }
                    getByName("release") {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                    }
                }
            }
        }
    }
}

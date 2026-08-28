package com.louis993546.metro.buildlogic

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MetroAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("metro.detekt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                val consumerRules = file("consumer-rules.pro")
                if (consumerRules.exists()) {
                    defaultConfig.consumerProguardFiles(consumerRules)
                }
            }
        }
    }
}

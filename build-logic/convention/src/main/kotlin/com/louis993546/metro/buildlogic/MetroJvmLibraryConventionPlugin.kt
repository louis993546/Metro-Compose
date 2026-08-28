package com.louis993546.metro.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class MetroJvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("metro.detekt")
            }

            configureKotlin()

            dependencies {
                add("testImplementation", libs.findLibrary("junit").get())
            }
        }
    }
}

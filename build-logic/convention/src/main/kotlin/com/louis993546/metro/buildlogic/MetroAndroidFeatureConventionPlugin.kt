package com.louis993546.metro.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MetroAndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("metro.android.library")
                apply("metro.android.compose")
            }

            dependencies {
                add("implementation", project(":metro"))
            }
        }
    }
}

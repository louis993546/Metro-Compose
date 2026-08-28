package com.louis993546.metro.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension,
) {
    commonExtension.buildFeatures.compose = true

    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    dependencies {
        val composeBom = platform(libs.findLibrary("compose.bom").get())
        add("implementation", composeBom)
        add("androidTestImplementation", composeBom)
        add("implementation", libs.findLibrary("compose.ui").get())
        add("implementation", libs.findLibrary("compose.material").get())
        add("implementation", libs.findLibrary("compose.ui.tooling").get())
    }
}

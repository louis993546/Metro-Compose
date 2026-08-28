package com.louis993546.metro.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class MetroAndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.findByType(LibraryExtension::class.java)
                ?: extensions.findByType(ApplicationExtension::class.java)
                ?: extensions.findByType(CommonExtension::class.java)
                ?: error("Android extension not found when applying Compose convention plugin")
            configureAndroidCompose(extension)
        }
    }
}

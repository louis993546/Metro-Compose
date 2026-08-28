package com.louis993546.metro.buildlogic

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class MetroDetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            extensions.configure<DetektExtension> {
                config.setFrom(rootProject.file("config/detekt/detekt.yml"))
                buildUponDefaultConfig = true
                baseline = file("$projectDir/detekt-baseline.xml")
            }
        }
    }
}

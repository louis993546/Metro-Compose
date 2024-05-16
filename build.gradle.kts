// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("compile_sdk_version", 34)
        set("build_tool_version", "34.0.0")
        set("min_sdk_version", 23)
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    }
}

plugins {
    alias(libs.plugins.binary.compatability.validator)
    alias(libs.plugins.doctor)
    alias(libs.plugins.detekt)
    alias(libs.plugins.module.graph)

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.compose) apply false
}

apiValidation {
    ignoredProjects.addAll(
        listOf(
            "demo",
            "demoApps",
            "demoAppDrawer",
            "demoAppRow",
            "demoAppSearch",
            "demoBrowser",
            "demoCalculator",
            "demoCalendar",
            "demoCamera",
            "demoLauncher",
            "demoMetroSettings",
            "demoRadio",
            "demoSettings",
            "demoWordle",
            "seattle"
        )
    )
}

detekt {
    config.setFrom(file("$project.rootDir/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

doctor {
    disallowMultipleDaemons = (System.getenv("CI") == "false")
    GCWarningThreshold = 0.10f
    GCFailThreshold = 0.9f
    failOnEmptyDirectories = true
    warnWhenJetifierEnabled = true
    negativeAvoidanceThreshold = 500
    warnWhenNotUsingParallelGC = true
    disallowCleanTaskDependencies = true
    warnIfKotlinCompileDaemonFallback = true
    javaHome {
        ensureJavaHomeMatches = true
        ensureJavaHomeIsSet = true
        failOnError.set(true)
    }
}

moduleGraphConfig {
    readmePath.set("./README.md")
    heading.set("### Graph")
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.layout.buildDirectory)
}

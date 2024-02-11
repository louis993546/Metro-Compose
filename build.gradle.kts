// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("kotlin_version", "2.0.0-Beta1")
        set("kotlin_compiler_version", "1.5.5-dev-k2.0.0-Beta1-06b8ae672a4")
        set("protofbuf_version", "3.25.1")

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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${rootProject.extra.get("kotlin_version")}")
    }
}

plugins {
    alias(libs.plugins.binary.compatability.validator)
    alias(libs.plugins.doctor)
    alias(libs.plugins.detekt)
    alias(libs.plugins.module.graph)
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

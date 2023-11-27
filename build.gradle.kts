// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("compose_bom_version", "2023.10.01")
        set("accompanist_version", "0.32.0")
        set("kotlin_version", "1.9.0")
        set("kotlin_compiler_version", "1.5.1")
        set("datastore_version", "1.0.0")
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
        classpath("com.android.tools.build:gradle:8.3.0-alpha13")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${rootProject.extra.get("kotlin_version")}")
    }
}

plugins {
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"
    id("com.osacky.doctor") version "0.9.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
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

tasks.register("clean", Delete::class.java) {
    delete(rootProject.layout.buildDirectory)
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("compose_bom_version", "2023.08.00")
        set("accompanist_version", "0.31.6-rc")
        set("kotlin_version", "1.9.0")
        set("kotlin_compiler_version", "1.5.1")
        set("datastore_version", "1.0.0")
        set("protofbuf_version", "3.24.0")
        set("compile_sdk_version", 34)
        set("build_tool_version", "34.0.0")
        set("min_sdk_version", 23)
    }
}

plugins {
    id("org.jetbrains.kotlinx.binary-compatibility-validator").version("0.13.2")
    id("com.osacky.doctor").version("0.8.1")
    id("io.gitlab.arturbosch.detekt").version("1.23.1")
    id ("com.android.tools.build:gradle").version("8.2.0-alpha15").apply(false)
    id ("org.jetbrains.kotlin:kotlin-gradle-plugin").version("1.9.0").apply(false) // TODO kotlin_version
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
        ensureJavaHomeMatches.set(true)
        ensureJavaHomeIsSet.set(true)
        failOnError.set(true)
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
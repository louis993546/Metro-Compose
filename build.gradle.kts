// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.binary.compatability.validator)
    alias(libs.plugins.doctor)
    alias(libs.plugins.detekt)
    alias(libs.plugins.module.graph)

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
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
            "seattle",
            "skylight",
        ),
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
    warnWhenJetifierEnabled = true
    negativeAvoidanceThreshold = 500
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

tasks.register<Delete>("clean") {
    description = "Remove all build folders"
    delete(rootProject.layout.buildDirectory)
}

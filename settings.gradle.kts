pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.gradle.develocity") version "4.4.3"
    }
}
plugins {
    id("com.gradle.develocity")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
        if (System.getenv("CI").toBoolean()) {
            tag("CI")
        } else {
            tag("Local")
        }
    }
}

rootProject.name = "Metro"
include(
    ":metro",
    ":verticalTilesGrid",
    ":seattle",
    ":skylight",
    ":demo",
    ":demoAppDrawer",
    ":demoAppRow",
    ":demoApps",
    ":demoAppSearch",
    ":demoBrowser",
    ":demoCalculator",
    ":demoCalendar",
    ":demoCamera",
    ":demoLauncher",
    ":demoMetroSettings",
    ":demoRadio",
    ":demoSettings",
    ":demoWordle",
)

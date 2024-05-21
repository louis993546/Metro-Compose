pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.gradle.develocity") version "3.17.4"
    }
}
plugins {
    id("com.gradle.develocity")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
//            content {
//                includeGroupAndSubgroups("androidx")
//                includeGroupAndSubgroups("com.android")
//                includeGroupAndSubgroups("com.google")
//            }
        }
        mavenCentral()
        maven("https://androidx.dev/storage/compose-compiler/repository/") {
            name = "Compose Compiler Snapshots"
            content { includeGroup("androidx.compose.compiler") }
        }
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

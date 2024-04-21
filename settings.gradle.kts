pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.gradle.develocity") version "3.17.2"
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
        maven {
            setUrl("https://androidx.dev/storage/compose-compiler/repository/")
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

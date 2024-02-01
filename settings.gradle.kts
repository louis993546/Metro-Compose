pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.gradle.enterprise") version "3.16.2"
    }
}
plugins {
    id("com.gradle.enterprise")
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
gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
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

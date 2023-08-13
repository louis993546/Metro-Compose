pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.gradle.enterprise").version("3.14.1")
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
    }
}
gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
        if (System.getenv("CI")?.isNotBlank() == true) {
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
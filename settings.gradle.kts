pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.gradle.enterprise") version "3.15.1"
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
        if (System.getenv("CI").toBoolean()) {
            tag("CI")
        } else {
            tag("Local")
        }
    }
}
rootProject.name = "Metro"
include(":metro")
include(":verticalTilesGrid")
include(":seattle")
include(":demo")
include(":demoAppDrawer")
include(":demoAppRow")
include(":demoApps")
include(":demoAppSearch")
include(":demoBrowser")
include(":demoCalculator")
include(":demoCalendar")
include(":demoCamera")
include(":demoLauncher")
include(":demoMetroSettings")
include(":demoRadio")
include(":demoSettings")
include(":demoWordle")
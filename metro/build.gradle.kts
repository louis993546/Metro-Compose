import java.net.URI

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
//    id("maven-publish")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.louis993546.metro"

    compileSdk = rootProject.extra.get("compile_sdk_version") as? Int

    defaultConfig {
        minSdk = rootProject.extra.get("min_sdk_version") as? Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }

//    publishing {
//        singleVariant("release") {
//            withSourcesJar()
//        }
//    }

    lint {
        disable.add("OpaqueUnitKey") // bug in androidGradlePlugin 8.6.0-alpha06
    }
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
}

dependencies {
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    api("androidx.compose.ui:ui")
    api("androidx.compose.foundation:foundation")
    api("androidx.compose.ui:ui-tooling")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestApi("androidx.compose.ui:ui-test-junit4")
}
//
//afterEvaluate {
//    publishing {
//        publications {
//            register("release", MavenPublication::class.java) {
//                from(components["release"])
//                val number = System.getenv("GITHUB_RUN_NUMBER") ?: 9999
//                groupId = "com.louis993546"
//                artifactId = "metro"
//                version = "0.$number.0"
//            }
//        }
//        repositories {
//            maven {
//                name = "GitHubPackages"
//                url = URI.create("https://maven.pkg.github.com/louis993546/Metro-Compose")
//                credentials {
//                    username = System.getenv("GITHUB_ACTOR")
//                    password = System.getenv("GITHUB_TOKEN")
//                }
//
//            }
//        }
//    }
//}

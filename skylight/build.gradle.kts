plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.louis993546.skylight"
    compileSdk = rootProject.extra.get("compile_sdk_version") as? Int

    defaultConfig {
        applicationId = "com.louis993546.skylight"
        minSdk = rootProject.extra.get("min_sdk_version") as? Int ?: 0
        targetSdk = rootProject.extra.get("compile_sdk_version") as? Int

        val number = System.getenv("GITHUB_RUN_NUMBER")?.toInt() ?: 1
        versionCode = number
        versionName = "0.${number}.0"
    }
    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.activity.compose)

    implementation(project(":metro"))

    implementation(libs.timber)

    testImplementation(libs.junit)
}
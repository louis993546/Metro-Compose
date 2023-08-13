plugins {
    kotlin("android")
    id("io.gitlab.arturbosch.detekt")
    id("com.android.library")
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

android {
    compileSdk = 34 //compile_sdk_version

    defaultConfig {
        minSdk = 23 //min_sdk_version
        targetSdk = 34 //compile_sdk_version

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = kotlin_compiler_version
    }
}

dependencies {
    implementation project(path: ":metro")
    testImplementation "junit:junit:4.13.2"
    androidTestImplementation "androidx.test.ext:junit:1.1.5"
    androidTestImplementation "androidx.test.espresso:espresso-core:3.5.1"
}
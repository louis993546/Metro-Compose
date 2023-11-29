// TODO I need another convention to share anything compose i guess (app/lib/util)
plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.louis993546.metro.vertical_tiles_grid"

    compileSdk = rootProject.extra.get("compile_sdk_version") as? Int

    defaultConfig {
        minSdk = rootProject.extra.get("min_sdk_version") as? Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        kotlinCompilerExtensionVersion = rootProject.extra.get("kotlin_compiler_version") as? String
    }
}

dependencies {
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.timber)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
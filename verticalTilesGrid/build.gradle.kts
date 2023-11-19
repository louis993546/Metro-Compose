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
        targetSdk = rootProject.extra.get("compile_sdk_version") as? Int

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
    val composeBom = platform("androidx.compose:compose-bom:${rootProject.extra.get("compose_bom_version")}")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("com.jakewharton.timber:timber:5.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
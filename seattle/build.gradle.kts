plugins {
    id("com.android.application")
    id("kotlin-android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.louis993546.seattle"
    defaultConfig {
        applicationId = "com.louis993546.seattle"
        minSdk = rootProject.extra.get("min_sdk_version") as? Int ?: 0
        compileSdk = rootProject.extra.get("compile_sdk_version") as? Int
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
    implementation(libs.androidx.activity.compose)
    implementation("androidx.compose.ui:ui-tooling")
    implementation(libs.material)

    implementation(project(":metro"))
    implementation(project(":demoAppRow")) // TODO move it into metro maybe?

    implementation(libs.timber)

    testImplementation(libs.junit)
}

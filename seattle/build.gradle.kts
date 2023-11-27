plugins {
    id("com.android.application")
    id("kotlin-android")
    id("io.gitlab.arturbosch.detekt")
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
    val composeBom = platform("androidx.compose:compose-bom:${rootProject.extra.get("compose_bom_version")}")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("com.google.android.material:material:1.10.0")

    implementation(project(":metro"))
    implementation(project(":demoAppRow")) // TODO move it into metro maybe?

    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
}
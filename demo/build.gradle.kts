plugins {
    id("com.android.application")
    id("kotlin-android")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.louis993546.metro.demo"
    compileSdk = rootProject.extra.get("compile_sdk_version") as? Int

    defaultConfig {
        applicationId = "com.louis993546.metro.demo"
        minSdk = rootProject.extra.get("min_sdk_version") as? Int
        compileSdk = rootProject.extra.get("compile_sdk_version") as? Int
        targetSdk = rootProject.extra.get("compile_sdk_version") as? Int

        val number = System.getenv("GITHUB_RUN_NUMBER")?.toInt() ?: 1
        versionCode = number
        versionName = "0.${number}.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    // K2 lint crash
    lint {
        disable.addAll(listOf("MutableCollectionMutableState", "AutoboxingStateCreation"))
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:${rootProject.extra.get("compose_bom_version")}")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(project(":metro"))
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.google.accompanist:accompanist-pager:${rootProject.extra.get("accompanist_version")}")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation(project(":demoApps"))

    //region Apps
    implementation(project(":demoAppDrawer"))
    implementation(project(":demoAppSearch"))
    implementation(project(":demoBrowser"))
    implementation(project(":demoCalculator"))
    implementation(project(":demoCalendar"))
    implementation(project(":demoLauncher"))
    implementation(project(":demoMetroSettings"))
    implementation(project(":demoRadio"))
    implementation(project(":demoSettings"))
    implementation(project(":demoWordle"))
    //endregion

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}

//tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
//    kotlinOptions {
//        freeCompilerArgs += listOf("\"-opt-in=org.mylibrary.OptInAnnotation\"")
//    }
//}
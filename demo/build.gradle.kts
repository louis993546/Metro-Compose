plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose)
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
}

dependencies {
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
    implementation(libs.accompanist.pager)
    implementation(libs.androidx.navigation.compose)
    implementation(project(":metro"))
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

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}

//tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
//    kotlinOptions {
//        freeCompilerArgs += listOf("\"-opt-in=org.mylibrary.OptInAnnotation\"")
//    }
//}
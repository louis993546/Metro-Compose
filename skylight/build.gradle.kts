plugins {
    id("metro.android.application")
    id("metro.android.compose")
}

android {
    namespace = "com.louis993546.skylight"

    defaultConfig {
        applicationId = "com.louis993546.skylight"
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(project(":metro"))
    implementation(libs.timber)
}
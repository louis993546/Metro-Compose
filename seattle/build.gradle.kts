plugins {
    id("metro.android.application")
    id("metro.android.compose")
}

android {
    namespace = "com.louis993546.seattle"

    defaultConfig {
        applicationId = "com.louis993546.seattle"
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
    implementation(project(":metro"))
    implementation(project(":demoAppRow"))
    implementation(libs.timber)
}

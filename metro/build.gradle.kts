plugins {
    id("metro.android.library")
    id("metro.android.compose")
}

android {
    namespace = "com.louis993546.metro"
}

dependencies {
    api(libs.compose.ui)
    api(libs.compose.foundation)
    api(libs.compose.ui.tooling)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

plugins {
    id("metro.android.library")
    id("metro.android.compose")
}

android {
    namespace = "com.louis993546.metro.vertical_tiles_grid"
}

dependencies {
    implementation(libs.compose.foundation)
    implementation(libs.timber)
}
plugins {
    id("metro.android.feature")
}

android {
    namespace = "com.louis993546.metro.demo.camera"
}

dependencies {
    implementation(libs.camera.core)
    implementation(libs.camera.two)
    implementation(libs.accompanist.permissions)
}
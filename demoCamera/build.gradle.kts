plugins {
    id("metro-app-library-convention")
}

android {
    namespace = "com.louis993546.metro.demo.camera"
}

dependencies {
    val camerax_version = "1.3.0-alpha06"
    implementation(libs.camera.core)
    implementation(libs.camera.two)
//    implementation "androidx.camera:camera-lifecycle:$camerax_version"
//    implementation "androidx.camera:camera-video:$camerax_version"
//    implementation "androidx.camera:camera-view:$camerax_version"
//    implementation "androidx.camera:camera-extensions:$camerax_version"

    implementation(libs.accompanist.permissions)
}
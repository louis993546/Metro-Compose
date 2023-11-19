plugins {
    id("metro-app-library-convention")
}

android {
    namespace = "com.louis993546.metro.demo.camera"
}

dependencies {
    val camerax_version = "1.3.0-alpha06"
    implementation("androidx.camera:camera-core:$camerax_version")
    implementation("androidx.camera:camera-camera2:$camerax_version")
//    implementation "androidx.camera:camera-lifecycle:$camerax_version"
//    implementation "androidx.camera:camera-video:$camerax_version"
//    implementation "androidx.camera:camera-view:$camerax_version"
//    implementation "androidx.camera:camera-extensions:$camerax_version"

    implementation("com.google.accompanist:accompanist-permissions:${rootProject.extra.get("accompanist_version")}")
}
plugins {
    id("metro-app-library-convention")
}

android {
    namespace = "com.louis993546.metro.demo.browser"
}

dependencies {
    implementation("com.google.accompanist:accompanist-webview:${rootProject.extra.get("accompanist_version")}")
}
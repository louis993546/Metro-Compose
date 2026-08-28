plugins {
    id("metro.android.feature")
}

android {
    namespace = "com.louis993546.metro.demo.appSearch"
}

dependencies {
    implementation(project(":demoApps"))
    implementation(project(":demoAppRow"))
}
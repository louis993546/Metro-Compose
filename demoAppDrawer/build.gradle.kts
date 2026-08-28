plugins {
    id("metro.android.feature")
}

android {
    namespace = "com.louis993546.metro.demo.appDrawer"
}

dependencies {
    implementation(project(":demoApps"))
    implementation(project(":demoAppRow"))
}
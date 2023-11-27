plugins {
    id("metro-app-library-convention")
}

android {
    namespace = "com.louis993546.metro.demo.appDrawer"
}

dependencies {
    implementation(project(":demoApps"))
    implementation(project(":demoAppRow"))
}
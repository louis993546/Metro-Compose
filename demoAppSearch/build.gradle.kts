plugins {
    id("metro-app-library-convention")
}

android {
    namespace = "com.louis993546.metro.demo.appSearch"
}

dependencies {
    implementation(project(":demoApps"))
    implementation(project(":demoAppRow"))
}
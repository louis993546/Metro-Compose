plugins {
    id("metro-app-library-convention")
}

android {
    namespace = "com.louis993546.metro.demo.appSearch"
    lint {
        disable.add("OpaqueUnitKey") // bug in androidGradlePlugin 8.6.0-alpha06
    }
}

dependencies {
    implementation(project(":demoApps"))
    implementation(project(":demoAppRow"))
}
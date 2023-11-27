plugins {
    id("metro-app-library-convention")
}

android {
    namespace = "com.louis993546.metro.demo.appSearch"

    // K2 lint crash
    lint {
        disable.add("MutableCollectionMutableState")
        disable.add("AutoboxingStateCreation")
    }
}

dependencies {
    implementation(project(":demoApps"))
    implementation(project(":demoAppRow"))
}
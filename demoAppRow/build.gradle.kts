plugins {
    id("metro-app-library-convention")
}

android {
    namespace = "com.louis993546.metro.demo.appRow"
}

dependencies {
    implementation(project(":demoApps"))
    implementation(libs.accompanist.drawablepainter)
}
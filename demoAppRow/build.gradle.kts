plugins {
    id("metro.android.feature")
}

android {
    namespace = "com.louis993546.metro.demo.appRow"
}

dependencies {
    implementation(project(":demoApps"))
    implementation(libs.accompanist.drawablepainter)
}
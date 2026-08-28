plugins {
    id("metro.android.application")
    id("metro.android.compose")
}

android {
    namespace = "com.louis993546.metro.demo"

    defaultConfig {
        applicationId = "com.louis993546.metro.demo"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
    implementation(libs.accompanist.pager)
    implementation(libs.androidx.navigation.compose)
    implementation(project(":metro"))
    implementation(project(":demoApps"))

    //region Apps
    implementation(project(":demoAppDrawer"))
    implementation(project(":demoAppSearch"))
    implementation(project(":demoBrowser"))
    implementation(project(":demoCalculator"))
    implementation(project(":demoCalendar"))
    implementation(project(":demoLauncher"))
    implementation(project(":demoMetroSettings"))
    implementation(project(":demoRadio"))
    implementation(project(":demoSettings"))
    implementation(project(":demoWordle"))
    //endregion

    androidTestImplementation(libs.androidx.ui.test.junit4)
}
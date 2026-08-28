plugins {
    `kotlin-dsl`
}

group = "com.louis993546.metro.buildlogic"

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.compose.gradle.plugin)
    implementation(libs.detekt.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "metro.android.application"
            implementationClass = "com.louis993546.metro.buildlogic.MetroAndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "metro.android.library"
            implementationClass = "com.louis993546.metro.buildlogic.MetroAndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "metro.android.compose"
            implementationClass = "com.louis993546.metro.buildlogic.MetroAndroidComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "metro.android.feature"
            implementationClass = "com.louis993546.metro.buildlogic.MetroAndroidFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "metro.jvm.library"
            implementationClass = "com.louis993546.metro.buildlogic.MetroJvmLibraryConventionPlugin"
        }
        register("detekt") {
            id = "metro.detekt"
            implementationClass = "com.louis993546.metro.buildlogic.MetroDetektConventionPlugin"
        }
    }
}

plugins {
    id("metro-app-library-convention")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.louis993546.metro.demo.wordle"

    lint {
        disable.add("OpaqueUnitKey") // bug in androidGradlePlugin 8.6.0-alpha06
    }
}
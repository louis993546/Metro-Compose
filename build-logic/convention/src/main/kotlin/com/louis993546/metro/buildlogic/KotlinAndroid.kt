package com.louis993546.metro.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension,
) {
    val compileSdkVersion = libs.findVersion("compileSdk").get().requiredVersion.toInt()
    val minSdkVersion = libs.findVersion("minSdk").get().requiredVersion.toInt()

    commonExtension.apply {
        compileSdk = compileSdkVersion

        defaultConfig.minSdk = minSdkVersion
        defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        compileOptions.sourceCompatibility = JavaVersion.VERSION_21
        compileOptions.targetCompatibility = JavaVersion.VERSION_21

        lint.disable.add("OpaqueUnitKey")
    }

    configureKotlin()

    dependencies {
        add("testImplementation", libs.findLibrary("junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
    }
}

internal fun Project.configureKotlin() {
    val jvmToolchainVersion = libs.findVersion("jvmToolchain").get().requiredVersion.toInt()

    extensions.findByType(KotlinAndroidProjectExtension::class.java)?.apply {
        jvmToolchain(jvmToolchainVersion)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    extensions.findByType(KotlinJvmProjectExtension::class.java)?.apply {
        jvmToolchain(jvmToolchainVersion)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}

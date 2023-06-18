@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    namespace = "com.github.zharovvv.core.ui"
}

dependencies {
    api(libs.material)
    api(libs.androidx.constraintlayout)
    implementation(libs.bundles.core)
}
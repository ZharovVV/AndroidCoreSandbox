@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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

    buildFeatures {
        viewBinding = true
    }
    namespace = "com.github.zharovvv.photo.editor.sandbox"
}

dependencies {
    //region DI
    implementation(project(":core-di"))
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    //endregion

    //region UI
    implementation(project(":core-ui"))
    //endregion

    //region navigation
    implementation(project(":core-navigation"))
    //endregion

    implementation(libs.bundles.core)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.coroutines.android)
}
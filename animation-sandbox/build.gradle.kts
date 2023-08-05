@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.github.zharovvv.animationsandbox"
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
    implementation(libs.bundles.rxjava)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
}
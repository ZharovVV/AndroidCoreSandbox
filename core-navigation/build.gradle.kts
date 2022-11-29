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
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.github.zharovvv.core.navigation"
}

dependencies {
    implementation(project(":core-ui"))
    implementation(libs.bundles.core)
}
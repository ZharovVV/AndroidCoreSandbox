plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    defaultConfig {
        applicationId = "com.github.zharovvv.sandboxx"
        versionCode = 1
        versionName = "1.0"
    }
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
    namespace = "com.github.zharovvv.sandboxx"
}

dependencies {
    //region DI
    implementation(project(":core-di"))
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    //endregion

    //region-ui
    implementation(project(":core-ui"))
    //endregion

    //region navigation
    implementation(project(":core-navigation"))
    //endregion

    //region Features
    implementation(project(":android-core-sandbox"))
    implementation(project(":rx-java-sandbox"))
    implementation(project(":animation-sandbox"))
    implementation(project(":compose-sandbox"))
    implementation(project(":android-accessibility"))
    implementation(project(":graphics-3d"))
    implementation(project(":photo-editor-sandbox"))
    //endregion

    implementation(libs.bundles.core)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
}
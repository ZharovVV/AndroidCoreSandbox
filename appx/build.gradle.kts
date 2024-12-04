@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    //https://github.com/gradle/gradle/issues/20084
    //the combination of buildSrc and plugins.alias remains problematic
    id(libs.plugins.my.plugin.get().pluginId)
    id("com.github.zharovvv.gradle.my-precompile-plugin")
}

//./gradlew createFileTask
createFile {
    message = "Message from app/build.gradle.kts"
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
    implementation(project(":auth-core"))
    //endregion

    implementation(libs.bundles.core)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
}
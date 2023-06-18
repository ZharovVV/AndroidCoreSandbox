@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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
    namespace = "com.github.zharovvv.compose.sandbox"
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
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.livedata)
    implementation(libs.compose.rxjava2)
    implementation("com.google.accompanist:accompanist-pager:0.24.11-rc")
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.coroutines.android)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(libs.compose.ui.test.junit4)
}
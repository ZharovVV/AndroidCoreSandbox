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
        //TODO раскоментировать, когда выйдет версия компоуза 1.2.0
        //kotlinCompilerExtensionVersion = libs.versions.compose.orNull
        kotlinCompilerExtensionVersion = "1.2.0"
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

    implementation(libs.bundles.core)
    implementation(libs.bundles.jetpack.compose)
    implementation("com.google.accompanist:accompanist-pager:0.24.11-rc")
    implementation("androidx.navigation:navigation-compose:2.5.0")
    implementation(libs.coroutines)

    //region fix bug compose preview
    //https://stackoverflow.com/questions/71812710/can-no-longer-view-jetpack-compose-previews-failed-to-instantiate-one-or-more-c
    debugImplementation("androidx.customview:customview:1.2.0-alpha01")
    debugImplementation("androidx.customview:customview-poolingcontainer:1.0.0-rc01")
    //endregion
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(libs.androidTest.compose)
}
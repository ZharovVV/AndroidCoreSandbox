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
        dataBinding = true
        viewBinding = true //Enabled View Binding
    }
}

dependencies {
// Начиная с Kotlin 1.4.
// Вам больше не нужно объявлять зависимость от библиотеки stdlib в любом проекте Kotlin Gradle,
// включая мультиплатформенный. Зависимость добавляется по умолчанию.
//
// implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    //region DI
    implementation(project(":core-di"))
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    //endregion

    //region UI
    implementation(project(":core-ui"))
    //endregion

    //region feature
    implementation(project(":rx-java-sandbox"))
    implementation(project(":animation-sandbox"))
    //endregion

    implementation(libs.bundles.core)
    implementation(libs.bundles.rxjava)
    implementation(libs.bundles.jetpack.workmanager)
    implementation(libs.bundles.jetpack.navigation)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
}
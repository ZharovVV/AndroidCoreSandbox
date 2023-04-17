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
    namespace = "com.github.zharovvv.graphics"
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
    implementation(libs.bundles.jetpack.navigation)
    implementation(libs.coroutines)
    //Не подходит из-за:
    // android:minSdkVersion="28"
    // android:compileSdkVersion="33"
     implementation("io.github.sceneview:sceneview:1.0.2")
    //Ниже более старая версия на Java.
    //дополнительно требует kotlinx-coroutines-jdk8 (Android 24)
    // чтобы было удобно работать с CompletableFuture в корутинах.
//    implementation("com.gorisse.thomas.sceneform:sceneform:1.21.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.1")
}
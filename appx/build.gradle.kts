plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 23
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//TODO разобраться с управлением зависимостями в gradle
//(указать зависимости в одном месте и далее как-то удобно использовать в каждом модуле)
dependencies {
    //region DI
    implementation(project(":core-di"))
    implementation("com.google.dagger:dagger:${rootProject.extra["dagger_2_version"]}")
    kapt("com.google.dagger:dagger-compiler:${rootProject.extra["dagger_2_version"]}")
    //endregion

    //region Features
    implementation(project(":rxJavaSandboxModule"))
    //endregion
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
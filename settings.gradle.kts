rootProject.name = "Android Core Sandbox"
include(
    ":appx",
    ":core-di",
    ":core-ui",
    ":core-navigation",
    ":android-core-sandbox",
    ":rx-java-sandbox",
    ":animation-sandbox",
    ":compose-sandbox",
    ":android-accessibility",
    ":graphics-3d"
)

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.7.0") //такая версия из-за Compose
            // Версия Компилятора Compose 1.2.0 совместимая c kotlin 1.7.0

            //android
            library("androidx-core-ktx", "androidx.core:core-ktx:1.8.0")
            library("androidx-appcompat", "androidx.appcompat:appcompat:1.4.2")
            library("androidx-constraintlayout", "androidx.constraintlayout:constraintlayout:2.1.3")
            library("material", "com.google.android.material:material:1.5.0")
            //for delegating ViewModel (by viewModels())
            library("androidx-activity-ktx", "androidx.activity:activity-ktx:1.5.0")
            bundle(
                "core",
                listOf(
                    "androidx-core-ktx",
                    "androidx-appcompat",
                    "androidx-constraintlayout",
                    "material",
                    "androidx-activity-ktx"
                )
            )
            //jetpack
            //aac
            library("jetpack-workmanager", "androidx.work:work-runtime:2.7.1")
            library("jetpack-workmanager-ktx", "androidx.work:work-runtime-ktx:2.7.1")
            bundle("jetpack-workmanager", listOf("jetpack-workmanager", "jetpack-workmanager-ktx"))

            //где-то тут тянется библиотека ktx Fragment с функциями-расширениями типа commit {..}
            library(
                "jetpack-navigation-fragment",
                "androidx.navigation:navigation-fragment-ktx:2.4.2"
            )
            library("jetpack-navigation-ui", "androidx.navigation:navigation-ui-ktx:2.4.2")
            bundle(
                "jetpack-navigation",
                listOf("jetpack-navigation-fragment", "jetpack-navigation-ui")
            )
            //compose
            version("compose", "1.2.0")
            library("jetpack-compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
            // Tooling support (Previews, etc.)
            library("jetpack-compose-ui-tooling", "androidx.compose.ui", "ui-tooling")
                .versionRef("compose")
            // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
            library("jetpack-compose-foundation", "androidx.compose.foundation", "foundation")
                .versionRef("compose")
            // Material Design
            library(
                "jetpack-compose-material3",
                "androidx.compose.material3:material3:1.0.0-alpha14"
            )
            library(
                "jetpack-compose-material-icons-core",
                "androidx.compose.material",
                "material-icons-core"
            )
                .versionRef("compose")
            library(
                "jetpack-compose-material-icons-extended",
                "androidx.compose.material",
                "material-icons-extended"
            )
                .versionRef("compose")
            // Integration with activities
            library("jetpack-compose-activity", "androidx.activity:activity-compose:1.4.0")
            // Integration with ViewModels
            library(
                "jetpack-compose-viewmodel",
                "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0"
            )
            // Integration with observables
            library("jetpack-compose-livedata", "androidx.compose.runtime:runtime-livedata:1.1.1")
            library("jetpack-compose-rxjava2", "androidx.compose.runtime:runtime-rxjava2:1.1.1")
            bundle(
                "jetpack-compose",
                listOf(
                    "jetpack-compose-ui",
                    "jetpack-compose-ui-tooling",
                    "jetpack-compose-foundation",
                    "jetpack-compose-material3",
                    "jetpack-compose-material-icons-core",
                    "jetpack-compose-material-icons-extended",
                    "jetpack-compose-activity",
                    "jetpack-compose-viewmodel",
                    "jetpack-compose-livedata",
                    "jetpack-compose-rxjava2"
                )
            )

            //unit tests
            library("junit", "junit:junit:4.13.2")
            //maybe mockk
            bundle("test", listOf("junit"))

            //android tests
            library("android-junit", "androidx.test.ext:junit:1.1.3")
            library("android-espresso", "androidx.test.espresso:espresso-core:3.4.0")
            bundle("androidTest", listOf("android-junit", "android-espresso"))
            library("androidTest-compose", "androidx.compose.ui:ui-test-junit4:1.1.1")

            //dagger
            version("dagger2", "2.42")
            library("dagger", "com.google.dagger", "dagger").versionRef("dagger2")
            library("dagger-compiler", "com.google.dagger", "dagger-compiler")
                .versionRef("dagger2")

            //rxjava2
            library("rxjava2", "io.reactivex.rxjava2:rxjava:2.2.9")
            library("rxjava2-android", "io.reactivex.rxjava2:rxandroid:2.1.1")
            library("rxjava2-kotlin", "io.reactivex.rxjava2:rxkotlin:2.3.0")
            bundle("rxjava", listOf("rxjava2", "rxjava2-android", "rxjava2-kotlin"))

            //coroutines
            library("coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
            //транзитивно тянет kotlinx-coroutines-core-jvm
        }
    }
}


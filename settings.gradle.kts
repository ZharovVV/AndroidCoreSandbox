rootProject.name = "Android Core Sandbox"
include(
    ":appx",
    ":core-di",
    ":core-ui",
    ":android-core-sandbox",
    ":rx-java-sandbox",
    ":animation-sandbox",
    ":compose-sandbox"
)

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.7.0") //такая версия из-за Compose
            // Версия Компилятора Compose 1.2.0 совместима c kotlin 1.7.0

            //android
            alias("androidx-core-ktx").to("androidx.core:core-ktx:1.8.0")
            alias("androidx-appcompat").to("androidx.appcompat:appcompat:1.4.2")
            alias("androidx-constraintlayout").to("androidx.constraintlayout:constraintlayout:2.1.3")
            alias("material").to("com.google.android.material:material:1.5.0")
            //for delegating ViewModel (by viewModels())
            alias("androidx-activity-ktx").to("androidx.activity:activity-ktx:1.5.0")
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
            alias("jetpack-workmanager").to("androidx.work:work-runtime:2.7.1")
            alias("jetpack-workmanager-ktx").to("androidx.work:work-runtime-ktx:2.7.1")
            bundle("jetpack-workmanager", listOf("jetpack-workmanager", "jetpack-workmanager-ktx"))
            alias("jetpack-navigation-fragment").to("androidx.navigation:navigation-fragment-ktx:2.4.2")
            alias("jetpack-navigation-ui").to("androidx.navigation:navigation-ui-ktx:2.4.2")
            bundle(
                "jetpack-navigation",
                listOf("jetpack-navigation-fragment", "jetpack-navigation-ui")
            )
            //compose
            version("compose", "1.2.0-rc03")
            alias("jetpack-compose-ui").to("androidx.compose.ui", "ui").versionRef("compose")
            // Tooling support (Previews, etc.)
            alias("jetpack-compose-ui-tooling")
                .to("androidx.compose.ui", "ui-tooling")
                .versionRef("compose")
            // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
            alias("jetpack-compose-foundation")
                .to("androidx.compose.foundation", "foundation")
                .versionRef("compose")
            // Material Design
            alias("jetpack-compose-material")
                .to("androidx.compose.material", "material")
                .versionRef("compose")
            alias("jetpack-compose-material-icons-core")
                .to("androidx.compose.material", "material-icons-core")
                .versionRef("compose")
            alias("jetpack-compose-material-icons-extended")
                .to("androidx.compose.material", "material-icons-extended")
                .versionRef("compose")
            // Integration with activities
            alias("jetpack-compose-activity").to("androidx.activity:activity-compose:1.4.0")
            // Integration with ViewModels
            alias("jetpack-compose-viewmodel").to("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0")
            // Integration with observables
            alias("jetpack-compose-livedata").to("androidx.compose.runtime:runtime-livedata:1.1.1")
            alias("jetpack-compose-rxjava2").to("androidx.compose.runtime:runtime-rxjava2:1.1.1")
            bundle(
                "jetpack-compose",
                listOf(
                    "jetpack-compose-ui",
                    "jetpack-compose-ui-tooling",
                    "jetpack-compose-foundation",
                    "jetpack-compose-material",
                    "jetpack-compose-material-icons-core",
                    "jetpack-compose-material-icons-extended",
                    "jetpack-compose-activity",
                    "jetpack-compose-viewmodel",
                    "jetpack-compose-livedata",
                    "jetpack-compose-rxjava2"
                )
            )

            //unit tests
            alias("junit").to("junit:junit:4.13.2")
            //maybe mockk
            bundle("test", listOf("junit"))

            //android tests
            alias("android-junit").to("androidx.test.ext:junit:1.1.3")
            alias("android-espresso").to("androidx.test.espresso:espresso-core:3.4.0")
            bundle("androidTest", listOf("android-junit", "android-espresso"))
            alias("androidTest-compose").to("androidx.compose.ui:ui-test-junit4:1.1.1")

            //dagger
            version("dagger2", "2.42")
            alias("dagger").to("com.google.dagger", "dagger").versionRef("dagger2")
            alias("dagger-compiler").to("com.google.dagger", "dagger-compiler")
                .versionRef("dagger2")

            //rxjava2
            alias("rxjava2").to("io.reactivex.rxjava2:rxjava:2.2.9")
            alias("rxjava2-android").to("io.reactivex.rxjava2:rxandroid:2.1.1")
            alias("rxjava2-kotlin").to("io.reactivex.rxjava2:rxkotlin:2.3.0")
            bundle("rxjava", listOf("rxjava2", "rxjava2-android", "rxjava2-kotlin"))

            //coroutines
            alias("coroutines").to("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
            //транзитивно тянет kotlinx-coroutines-core-jvm
        }
    }
}


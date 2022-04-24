rootProject.name = "Android Core Sandbox"
include(
    ":android-core-sandbox",
    ":animation-sandbox",
    //new api
    ":appx",
    ":core-di",
    ":core-ui",
    ":rx-java-sandbox"
)

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.6.21")

            //android
            alias("androidx-core-ktx").to("androidx.core:core-ktx:1.7.0")
            alias("androidx-appcompat").to("androidx.appcompat:appcompat:1.4.1")
            alias("androidx-constraintlayout").to("androidx.constraintlayout:constraintlayout:2.1.3")
            alias("material").to("com.google.android.material:material:1.5.0")
            //for delegating ViewModel (by viewModels())
            alias("androidx-activity-ktx").to("androidx.activity:activity-ktx:1.4.0")
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

            //unit tests
            alias("junit").to("junit:junit:4.13.2")
            //maybe mockk
            bundle("test", listOf("junit"))

            //android tests
            alias("android-junit").to("androidx.test.ext:junit:1.1.3")
            alias("android-espresso").to("androidx.test.espresso:espresso-core:3.4.0")
            bundle("androidTest", listOf("android-junit", "android-espresso"))

            //dagger
            version("dagger2", "2.40.5")
            alias("dagger").to("com.google.dagger", "dagger").versionRef("dagger2")
            alias("dagger-compiler").to("com.google.dagger", "dagger-compiler")
                .versionRef("dagger2")

            //rxjava2
            alias("rxjava2").to("io.reactivex.rxjava2:rxjava:2.2.9")
            alias("rxjava2-android").to("io.reactivex.rxjava2:rxandroid:2.1.1")
            alias("rxjava2-kotlin").to("io.reactivex.rxjava2:rxkotlin:2.3.0")
            bundle("rxjava", listOf("rxjava2", "rxjava2-android", "rxjava2-kotlin"))
        }
    }
}


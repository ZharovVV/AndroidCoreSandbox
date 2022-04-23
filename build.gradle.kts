// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("kotlin_version", "1.6.20")
        set("rxjava2_version", "2.2.9")
        set("rxjava2_rxandroid_version", "2.1.1")
        set("rxjava2_rxkotlin_version", "2.3.0")
        set("dagger_2_version", "2.40.5")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val kotlinVersion = rootProject.extra["kotlin_version"]
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
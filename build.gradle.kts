// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Gradle bug https://github.com/gradle/gradle/issues/22797
@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.gradle.versions.filter) apply true
}

subprojects {
    project.plugins.applyBaseAndroidConfig(project)
    project.plugins.applyBaseJavaLibraryConfig(project(":core-di"))
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//region Extensions
fun PluginContainer.applyBaseAndroidConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is com.android.build.gradle.AppPlugin -> {
                project.extensions
                    .getByType<com.android.build.gradle.AppExtension>()
                    .apply {
                        baseAndroidConfig()
                    }
            }
            is com.android.build.gradle.LibraryPlugin -> {
                project.extensions
                    .getByType<com.android.build.gradle.LibraryExtension>()
                    .apply {
                        baseAndroidConfig()
                    }
            }
        }
    }
}

fun com.android.build.gradle.BaseExtension.baseAndroidConfig() {

    compileSdkVersion(33)

    defaultConfig.apply {
        minSdk = 28
        targetSdk = 32
        buildToolsVersion("32.0.0")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
//            useFir = true
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xcontext-receivers",
                "-opt-in=kotlin.RequiresOptIn",
                "-Xjvm-default=all"
            )
        }
    }
}

fun PluginContainer.applyBaseJavaLibraryConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is JavaLibraryPlugin -> {
                project.extensions
                    .getByType<JavaPluginExtension>()
                    .apply {
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }
            }
        }
    }
}
//endregion
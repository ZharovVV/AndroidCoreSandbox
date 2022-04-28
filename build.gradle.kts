// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
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

    compileSdkVersion(32)

    defaultConfig.apply {
        minSdk = 23
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
                        baseJavaConfig()
                    }
            }
        }
    }
}

fun JavaPluginExtension.baseJavaConfig() {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
//endregion
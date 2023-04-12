// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        //for se.ascp.gradle:gradle-versions-filter:0.1.16
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        val kotlinVersion = libs.versions.kotlin.get()
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("se.ascp.gradle:gradle-versions-filter:0.1.16")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

/**
 * TODO удалить, когда для versionCatalog заработают предложения обновления версий
 * [bug](https://issuetracker.google.com/issues/226078451)
 *
 * См. [описание работы](https://github.com/janderssonse/gradle-versions-filter-plugin)
 * Для запуска плагина нужно выполнить задачу:
 * ```
 * ./gradlew dependencyUpdates
 * ```
 */
apply(plugin = "se.ascp.gradle.gradle-versions-filter")

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
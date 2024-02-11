@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //for se.ascp.gradle:gradle-versions-filter
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}
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
    ":graphics-3d",
    ":photo-editor-sandbox"
)
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
rootDir.listFiles()
    ?.filter { it.isDirectory && it.name != "buildSrc" && (File(it, "build.gradle.kts").exists()) }
    ?.forEach { include(it.name) }
@Suppress("JavaPluginLanguageLevel")
//source и target Compatibility задаются для данного модуля в build.gradle.kts на уровне проекта
//(надеюсь это действительно работает :) )
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(libs.dagger)
}
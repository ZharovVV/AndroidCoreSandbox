import com.android.build.api.dsl.LibraryBuildType
import java.util.Properties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

val localProperties = Properties().apply {
    val localPropertiesFile = File(rootProject.rootDir, "local.properties")
    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use(::load)
    }
}

fun LibraryBuildType.buildGitHubSecretsConfigFrom(properties: Properties) {
    buildConfigField(
        type = "String", name = "CLIENT_ID", value = properties.getProperty("client.id")
    )
    buildConfigField(
        type = "String", name = "CLIENT_SECRET", value = properties.getProperty("client.secret")
    )
}

android {
    namespace = "com.github.zharovvv.auth.core"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    defaultConfig {
        manifestPlaceholders["appAuthRedirectScheme"] = "com.github.zharovvv.auth"
    }

    buildTypes {
        debug {
            buildGitHubSecretsConfigFrom(localProperties)
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildGitHubSecretsConfigFrom(localProperties)
        }
    }
}

dependencies {
    //region DI
    implementation(project(":core-di"))
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    //endregion

    //region UI
    implementation(project(":core-ui"))
    //endregion

    //region navigation
    implementation(project(":core-navigation"))
    //endregion

    implementation(libs.bundles.core)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.coroutines.android)
    implementation("net.openid:appauth:0.9.1")

    implementation(platform(libs.ktor.bom))
    implementation(libs.bundles.ktor)

    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-ktor2:3.0.4")
}

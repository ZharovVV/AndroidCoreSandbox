package com.github.zharovvv.sandboxx.startup

import android.content.Context
import com.github.zharovvv.common.di.DI
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureContainerManager
import com.github.zharovvv.common.di.FeatureContainerManagerImpl
import com.github.zharovvv.core.di.app.AppInitializerImpl
import com.github.zharovvv.sandboxx.SandboxXApplication
import com.github.zharovvv.sandboxx.di.holders.features.CommonFeaturesInitializer

class DiStartup {

    fun create(context: Context) = try {
        val application = context as SandboxXApplication
        val featureContainer: FeatureContainer = buildFeatureContainer(application)
        DI.initialize(featureContainer)
    } catch (e: Exception) {
        throw RuntimeException("Failed initialize DI", e)
    }

    private fun buildFeatureContainer(application: SandboxXApplication): FeatureContainer {
        val featureContainerManager: FeatureContainerManager = FeatureContainerManagerImpl()
        return featureContainerManager.init(
            AppInitializerImpl(
                CommonFeaturesInitializer(application, featureContainerManager)
            )
        )
    }
}
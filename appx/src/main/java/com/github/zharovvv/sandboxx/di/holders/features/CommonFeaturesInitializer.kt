package com.github.zharovvv.sandboxx.di.holders.features

import android.app.Application
import com.github.zharovvv.common.di.FeatureContainerManager
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.init.FeatureInitializer
import com.github.zharovvv.common.di.meta.FeatureApi
import kotlin.reflect.KClass

class CommonFeaturesInitializer(
    private val application: Application,
    private val featureContainerManager: FeatureContainerManager
) : FeatureInitializer {

    override fun initialize(): Map<KClass<out FeatureApi>, FeatureHolder<FeatureApi>> {
        return DaggerCommonFeatureHoldersComponent.factory()
            .create(application, featureContainerManager)
            .getFeatureHolders()
            .mapKeys { it.key.kotlin }
    }
}
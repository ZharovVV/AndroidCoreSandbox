package com.github.zharovvv.core.di.app

import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.init.FeatureInitializer
import com.github.zharovvv.common.di.meta.FeatureApi
import kotlin.reflect.KClass

class AppInitializerImpl(
    private val featureInitializer: FeatureInitializer
) : AppInitializer {

    override fun createFeatureHolders(
        featureContainer: FeatureContainer
    ): Map<KClass<out FeatureApi>, FeatureHolder<FeatureApi>> {
        return featureInitializer.initialize()
    }
}
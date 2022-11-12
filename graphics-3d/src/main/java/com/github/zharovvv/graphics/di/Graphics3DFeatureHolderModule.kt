package com.github.zharovvv.graphics.di

import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.graphics.di.api.Graphics3DApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class Graphics3DFeatureHolderModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(Graphics3DApi::class)]
    fun androidAccessibilityFeatureHolder(featureContainer: FeatureContainer): FeatureHolder<FeatureApi> {
        return Graphics3DFeatureHolder(featureContainer)
    }
}
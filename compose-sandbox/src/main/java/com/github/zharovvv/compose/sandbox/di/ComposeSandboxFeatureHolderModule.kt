package com.github.zharovvv.compose.sandbox.di

import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ComposeSandboxFeatureHolderModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(ComposeSandboxApi::class)]
    fun provideComposeSandboxFeatureHolder(featureContainer: FeatureContainer): FeatureHolder<FeatureApi> {
        return ComposeSandboxFeatureHolder(featureContainer)
    }
}
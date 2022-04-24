package com.github.zharovvv.animationsandbox.di

import com.github.zharovvv.animationsandbox.di.api.AnimationSandboxApi
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class AnimationSandboxFeatureHolderModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AnimationSandboxApi::class)]
    fun provideRxJavaSandboxFeatureHolder(featureContainer: FeatureContainer): FeatureHolder<FeatureApi> {
        return AnimationSandboxFeatureHolder(featureContainer)
    }
}
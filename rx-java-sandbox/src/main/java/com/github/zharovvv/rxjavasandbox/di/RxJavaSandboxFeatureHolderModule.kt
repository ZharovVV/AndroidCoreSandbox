package com.github.zharovvv.rxjavasandbox.di

import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class RxJavaSandboxFeatureHolderModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(RxJavaSandboxApi::class)]
    fun provideRxJavaSandboxFeatureHolder(featureContainer: FeatureContainer): FeatureHolder<FeatureApi> {
        return RxJavaSandboxFeatureHolder(featureContainer)
    }
}
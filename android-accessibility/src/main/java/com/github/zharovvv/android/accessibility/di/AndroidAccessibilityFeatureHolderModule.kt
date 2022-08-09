package com.github.zharovvv.android.accessibility.di

import com.github.zharovvv.android.accessibility.di.api.AndroidAccessibilityApi
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class AndroidAccessibilityFeatureHolderModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AndroidAccessibilityApi::class)]
    fun androidAccessibilityFeatureHolder(featureContainer: FeatureContainer): FeatureHolder<FeatureApi> {
        return AndroidAccessibilityFeatureHolder(featureContainer)
    }
}
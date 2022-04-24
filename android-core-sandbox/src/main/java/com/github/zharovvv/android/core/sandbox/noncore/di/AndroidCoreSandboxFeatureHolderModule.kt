package com.github.zharovvv.android.core.sandbox.noncore.di

import android.content.Context
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.common.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class AndroidCoreSandboxFeatureHolderModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AndroidCoreSandboxApi::class)]
    fun provideAndroidCoreSandboxFeatureHolder(
        featureContainer: FeatureContainer,
        @ApplicationContext applicationContext: Context
    ): FeatureHolder<FeatureApi> {
        return AndroidCoreSandboxSandboxFeatureHolder(featureContainer, applicationContext)
    }
}
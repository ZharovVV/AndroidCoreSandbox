package com.github.zharovvv.auth.core.di

import android.content.Context
import com.github.zharovvv.auth.core.di.api.AuthCoreApi
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
class AuthCoreFeatureHolderModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AuthCoreApi::class)]
    fun provideComposeSandboxFeatureHolder(
        featureContainer: FeatureContainer,
        @ApplicationContext applicationContext: Context
    ): FeatureHolder<FeatureApi> {
        return AuthCoreFeatureHolder(featureContainer, applicationContext)
    }
}
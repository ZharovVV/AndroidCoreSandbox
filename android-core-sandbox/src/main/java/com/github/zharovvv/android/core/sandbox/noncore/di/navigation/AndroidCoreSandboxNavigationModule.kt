package com.github.zharovvv.android.core.sandbox.noncore.di.navigation

import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class AndroidCoreSandboxNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AndroidCoreSandboxApi::class)]
    fun androidCoreSandboxEntryPoint(): EntryPoint =
        EntryPoint(
            name = "Android Core Sandbox",
            description = "Модуль-песочница для разбора основных компонентов Android SDK.",
            iconResId = R.drawable.ic_launcher_foreground,
            routerProvider = { featureApi<AndroidCoreSandboxApi>().router }
        )
}
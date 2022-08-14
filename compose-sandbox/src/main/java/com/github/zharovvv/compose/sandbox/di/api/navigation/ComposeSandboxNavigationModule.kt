package com.github.zharovvv.compose.sandbox.di.api.navigation

import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.compose.sandbox.R
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.core.navigation.EntryPoint
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ComposeSandboxNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(ComposeSandboxApi::class)]
    fun composeSandboxEntryPoint(): EntryPoint =
        EntryPoint(
            name = "Compose Sandbox",
            description = "Песочница для Jetpack Compose",
            iconResId = R.drawable.jetpack_compose_icon,
            routerProvider = { featureApi<ComposeSandboxApi>().router }
        )
}
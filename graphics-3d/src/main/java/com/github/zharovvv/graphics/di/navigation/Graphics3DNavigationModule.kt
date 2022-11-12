package com.github.zharovvv.graphics.di.navigation

import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.graphics.R
import com.github.zharovvv.graphics.di.api.Graphics3DApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class Graphics3DNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(Graphics3DApi::class)]
    fun androidAccessibilityEntryPoint(): EntryPoint =
        EntryPoint(
            name = "3D Graphics",
            description = "3D графика в Android",
            iconResId = R.drawable.ic_baseline_graphic_eq_24,
            routerProvider = { featureApi<Graphics3DApi>().router }
        )
}
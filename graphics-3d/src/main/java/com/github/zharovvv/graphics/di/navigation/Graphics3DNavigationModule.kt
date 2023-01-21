package com.github.zharovvv.graphics.di.navigation

import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint.ActivityEntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import com.github.zharovvv.graphics.R
import com.github.zharovvv.graphics.di.api.Graphics3DApi
import com.github.zharovvv.graphics.presentation.Graphics3DActivity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class Graphics3DNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(Graphics3DApi::class)]
    fun androidAccessibilityEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "3D Graphics",
            description = "3D графика в Android",
            iconResId = R.drawable.ic_baseline_graphic_eq_24,
            launcher = { context ->
                Graphics3DActivity.createIntent(context)
                    .let(context::startActivity)
            }
        )
}
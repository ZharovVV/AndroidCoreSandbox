package com.github.zharovvv.compose.sandbox.di.api.navigation

import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.compose.sandbox.R
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.compose.sandbox.ui.ComposeMainActivity
import com.github.zharovvv.core.navigation.EntryPoint.ActivityEntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class ComposeSandboxNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(ComposeSandboxApi::class)]
    fun composeSandboxEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Compose Sandbox",
            description = "Песочница для Jetpack Compose",
            iconResId = R.drawable.jetpack_compose_icon,
            launcher = { context ->
                context.startActivity(ComposeMainActivity.newIntent(context))
            }
        )
}
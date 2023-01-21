package com.github.zharovvv.android.core.sandbox.noncore.di.navigation

import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.TrueMainActivity
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint.ActivityEntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class AndroidCoreSandboxNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AndroidCoreSandboxApi::class)]
    fun androidCoreSandboxEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Android Core Sandbox",
            description = "Модуль-песочница для разбора основных компонентов Android SDK.",
            iconResId = R.drawable.ic_launcher_foreground,
            launcher = { context ->
                context.startActivity(TrueMainActivity.newIntent(context))
            }
        )
}
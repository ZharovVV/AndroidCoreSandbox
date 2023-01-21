package com.github.zharovvv.animationsandbox.di.navigation

import com.github.zharovvv.animationsandbox.R
import com.github.zharovvv.animationsandbox.di.api.AnimationSandboxApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint.ActivityEntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class AndroidAnimationNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AnimationSandboxApi::class)]
    fun androidAnimationEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Animation Sandbox",
            description = "Песочница для разбора анимаций в андроиде.",
            iconResId = R.drawable.ic_baseline_all_inclusive_24,
            launcher = AnimationSandboxLauncherImpl()
        )
}
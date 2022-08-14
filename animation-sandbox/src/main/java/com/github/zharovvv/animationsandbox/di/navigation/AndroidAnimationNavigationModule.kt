package com.github.zharovvv.animationsandbox.di.navigation

import com.github.zharovvv.animationsandbox.R
import com.github.zharovvv.animationsandbox.di.api.AnimationSandboxApi
import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class AndroidAnimationNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AnimationSandboxApi::class)]
    fun androidAnimationEntryPoint(): EntryPoint =
        EntryPoint(
            name = "Animation Sandbox",
            description = "Песочница для разбора анимаций в андроиде.",
            iconResId = R.drawable.ic_baseline_all_inclusive_24,
            routerProvider = { featureApi<AnimationSandboxApi>().router }
        )
}
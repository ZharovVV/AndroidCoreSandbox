package com.github.zharovvv.animationsandbox.di

import com.github.zharovvv.animationsandbox.di.api.AnimationSandboxApi
import com.github.zharovvv.animationsandbox.di.api.DaggerAnimationSandboxComponent
import com.github.zharovvv.common.di.BaseFeatureHolder
import com.github.zharovvv.common.di.FeatureContainer

class AnimationSandboxFeatureHolder(
    featureContainer: FeatureContainer
) : BaseFeatureHolder<AnimationSandboxApi>(featureContainer) {

    override fun buildFeature(): AnimationSandboxApi {
        return DaggerAnimationSandboxComponent.create()
    }
}
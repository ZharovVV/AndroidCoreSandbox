package com.github.zharovvv.compose.sandbox.di

import com.github.zharovvv.common.di.BaseFeatureHolder
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.compose.sandbox.di.api.DaggerComposeSandboxComponent

class ComposeSandboxFeatureHolder(
    featureContainer: FeatureContainer
) : BaseFeatureHolder<ComposeSandboxApi>(featureContainer) {

    override fun buildFeature(): ComposeSandboxApi {
        return DaggerComposeSandboxComponent.create()
    }
}
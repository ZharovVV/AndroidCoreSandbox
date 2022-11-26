package com.github.zharovvv.graphics.di

import android.content.Context
import com.github.zharovvv.common.di.BaseFeatureHolder
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.graphics.di.api.DaggerGraphics3DComponent
import com.github.zharovvv.graphics.di.api.Graphics3DApi

class Graphics3DFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<Graphics3DApi>(featureContainer) {

    override fun buildFeature(): Graphics3DApi {
        return DaggerGraphics3DComponent.factory()
            .create(applicationContext)
    }
}
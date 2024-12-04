package com.github.zharovvv.auth.core.di

import android.content.Context
import com.github.zharovvv.auth.core.di.api.AuthCoreApi
import com.github.zharovvv.auth.core.di.api.DaggerAuthCoreComponent
import com.github.zharovvv.common.di.BaseFeatureHolder
import com.github.zharovvv.common.di.FeatureContainer


class AuthCoreFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<AuthCoreApi>(featureContainer) {

    override fun buildFeature(): AuthCoreApi {
        return DaggerAuthCoreComponent.factory().create(applicationContext)
    }
}
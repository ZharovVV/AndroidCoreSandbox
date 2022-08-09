package com.github.zharovvv.android.accessibility.di

import com.github.zharovvv.android.accessibility.di.api.AndroidAccessibilityApi
import com.github.zharovvv.android.accessibility.di.api.DaggerAndroidAccessibilityComponent
import com.github.zharovvv.common.di.BaseFeatureHolder
import com.github.zharovvv.common.di.FeatureContainer

class AndroidAccessibilityFeatureHolder(
    featureContainer: FeatureContainer
) : BaseFeatureHolder<AndroidAccessibilityApi>(featureContainer) {

    override fun buildFeature(): AndroidAccessibilityApi {
        return DaggerAndroidAccessibilityComponent.create()
    }
}
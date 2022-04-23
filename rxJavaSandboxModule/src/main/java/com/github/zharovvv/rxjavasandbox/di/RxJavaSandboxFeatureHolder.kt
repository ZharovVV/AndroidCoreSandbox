package com.github.zharovvv.rxjavasandbox.di

import com.github.zharovvv.common.di.BaseFeatureHolder
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.rxjavasandbox.di.api.DaggerRxJavaSandboxComponent
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi

class RxJavaSandboxFeatureHolder(
    featureContainer: FeatureContainer
) : BaseFeatureHolder<RxJavaSandboxApi>(featureContainer) {

    override fun buildFeature(): RxJavaSandboxApi {
        return DaggerRxJavaSandboxComponent.create()
    }
}
package com.github.zharovvv.sandboxx.di.mainscreen.entrypoints

import com.github.zharovvv.common.di.BaseFeatureHolder
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.sandboxx.di.mainscreen.entrypoints.api.DaggerMainScreenEntryPointsComponent
import com.github.zharovvv.sandboxx.di.mainscreen.entrypoints.api.MainScreenEntryPointsApi

class MainScreenEntryPointsFeatureHolder(
    featureContainer: FeatureContainer
) : BaseFeatureHolder<MainScreenEntryPointsApi>(featureContainer) {

    override fun buildFeature(): MainScreenEntryPointsApi {
        return DaggerMainScreenEntryPointsComponent.create()
    }
}
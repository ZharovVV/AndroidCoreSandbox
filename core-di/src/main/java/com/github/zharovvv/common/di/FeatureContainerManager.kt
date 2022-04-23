package com.github.zharovvv.common.di

import com.github.zharovvv.core.di.app.AppInitializer

interface FeatureContainerManager : FeatureContainer {

    fun init(appInitializer: AppInitializer): FeatureContainerManager
}
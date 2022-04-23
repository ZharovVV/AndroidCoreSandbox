package com.github.zharovvv.common.di

import com.github.zharovvv.common.di.meta.FeatureApi
import kotlin.reflect.KClass

interface FeatureContainer {

    fun <Feature : FeatureApi> getFeature(key: KClass<Feature>): Feature

    fun <FeatureDependency : FeatureApi> getDependency(
        key: KClass<FeatureDependency>,
        targetFeatureHolderClass: KClass<*>
    ): FeatureDependency

    fun <Feature : FeatureApi> releaseFeature(key: KClass<Feature>)
}
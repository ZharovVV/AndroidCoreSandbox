package com.github.zharovvv.common.di

import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.core.di.app.AppInitializer
import com.github.zharovvv.core.di.models.error.DiApiNotFoundException
import kotlin.reflect.KClass

class FeatureContainerManagerImpl : FeatureContainerManager {

    private lateinit var featureHolders: Map<KClass<out FeatureApi>, FeatureHolder<FeatureApi>>

    override fun init(appInitializer: AppInitializer): FeatureContainerManager {
        featureHolders = appInitializer.createFeatureHolders(this)
        return this
    }

    override fun <Feature : FeatureApi> getFeature(key: KClass<Feature>): Feature {
        return getFeatureHolder(key).getFeature()
    }

    private fun <Feature : FeatureApi> getFeatureHolder(key: KClass<Feature>): FeatureHolder<Feature> {
        return retrieveFeatureHolder(key) ?: throw DiApiNotFoundException(key)
    }

    private fun <Feature : FeatureApi> retrieveFeatureHolder(
        key: KClass<Feature>
    ): FeatureHolder<Feature>? {
        @Suppress("UNCHECKED_CAST")
        return featureHolders[key]?.let { it as FeatureHolder<Feature> }
    }

    override fun <FeatureDependency : FeatureApi> getDependency(
        key: KClass<FeatureDependency>,
        targetFeatureHolderClass: KClass<*>
    ): FeatureDependency {
        return getFeatureHolder(key).getFeature()
    }

    override fun <Feature : FeatureApi> releaseFeature(key: KClass<Feature>) {
        retrieveFeatureHolder(key)?.releaseFeature()
    }
}
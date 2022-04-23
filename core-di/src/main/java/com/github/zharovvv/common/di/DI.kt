package com.github.zharovvv.common.di

import com.github.zharovvv.common.di.meta.FeatureApi
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * Класс для удобного извлечения / зачистки фичей
 */
object DI {

    private lateinit var featureContainer: FeatureContainer

    fun initialize(featureContainer: FeatureContainer) {
        this.featureContainer = featureContainer
    }

    fun <Feature : FeatureApi> getFeature(key: KClass<Feature>): Feature {
        return featureContainer.getFeature(key)
    }

    fun <ExternalApi : FeatureApi, InternalApi : ExternalApi> getInternalFeature(
        key: KClass<ExternalApi>,
        returnType: KClass<InternalApi>
    ): InternalApi {
        val feature: Any = getFeature(key)
        if (returnType.isInstance(feature)) {
            return returnType.cast(feature)
        } else {
            throw IllegalStateException("Feature with key $key should be instance of $returnType")
        }
    }

    fun <Feature : FeatureApi> releaseFeature(key: KClass<Feature>) {
        featureContainer.releaseFeature(key)
    }
}

inline fun <reified Feature : FeatureApi> featureApi(): Feature = DI.getFeature(Feature::class)

inline fun <reified ExternalApi : FeatureApi, reified InternalApi : ExternalApi> internalFeatureApi(): InternalApi =
    DI.getInternalFeature(ExternalApi::class, InternalApi::class)

inline fun <reified Feature : FeatureApi> releaseFeature() = DI.releaseFeature(Feature::class)
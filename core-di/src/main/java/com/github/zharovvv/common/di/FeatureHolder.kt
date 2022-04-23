package com.github.zharovvv.common.di

import com.github.zharovvv.common.di.meta.FeatureApi

/**
 * Контейнер экземпляра фичи
 */
interface FeatureHolder<out Feature : FeatureApi> {

    fun getFeature(): Feature

    fun releaseFeature()
}
package com.github.zharovvv.common.di.init

import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.meta.FeatureApi
import kotlin.reflect.KClass

interface FeatureInitializer {

    fun initialize(): Map<KClass<out FeatureApi>, FeatureHolder<FeatureApi>>
}
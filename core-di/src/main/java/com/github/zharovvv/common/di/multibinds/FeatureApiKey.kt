package com.github.zharovvv.common.di.multibinds

import com.github.zharovvv.common.di.meta.FeatureApi
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FeatureApiKey(val value: KClass<out FeatureApi>)

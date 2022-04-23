package com.github.zharovvv.common.di.meta

import kotlin.reflect.KClass

/**
 * Аннотация для указания Api, через которое можно получить экземпляр данного класса
 * в качестве зависимости.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ProvidedBy(val value: KClass<*>)

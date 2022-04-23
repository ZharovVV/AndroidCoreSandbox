package com.github.zharovvv.core.di.models.error

import kotlin.reflect.KClass

class DiApiNotFoundException(private val featureApiClass: KClass<*>) : RuntimeException() {
    override val message: String
        get() = "Requested Feature Api ${featureApiClass.qualifiedName} was not found"
}
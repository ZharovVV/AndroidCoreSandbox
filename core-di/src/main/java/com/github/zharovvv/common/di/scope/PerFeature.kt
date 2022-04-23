package com.github.zharovvv.common.di.scope

import com.github.zharovvv.common.di.FeatureHolder
import javax.inject.Scope

/**
 * Скоуп для фичевых зависимостей.
 * В случае применения зависимость будет сохраняться в памяти до вызова [FeatureHolder.releaseFeature].
 */
@Scope
annotation class PerFeature
package com.github.zharovvv.sandboxx.di.mainscreen.entrypoints.api

import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.core.navigation.EntryPoint
import kotlin.reflect.KClass

interface MainScreenEntryPointsApi : FeatureApi {

    val mainScreenEntryPointsMap: Map<KClass<out FeatureApi>, EntryPoint>
}
package com.github.zharovvv.sandboxx.di.mainscreen.entrypoints.api

import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import kotlin.reflect.KClass

interface MainScreenEntryPointsApi : FeatureApi {

    @OptIn(OnlyForMainScreen::class)
    val mainScreenEntryPointsMap: Map<KClass<out FeatureApi>, EntryPoint.ActivityEntryPoint>
}
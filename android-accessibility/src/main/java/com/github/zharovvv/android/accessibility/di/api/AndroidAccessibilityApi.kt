package com.github.zharovvv.android.accessibility.di.api

import com.github.zharovvv.android.accessibility.di.navigation.AndroidAccessibilityRouter
import com.github.zharovvv.common.di.meta.FeatureApi

interface AndroidAccessibilityApi : FeatureApi {

    val router: AndroidAccessibilityRouter
}
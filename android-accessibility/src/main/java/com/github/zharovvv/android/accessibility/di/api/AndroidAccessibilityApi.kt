package com.github.zharovvv.android.accessibility.di.api

import com.github.zharovvv.android.accessibility.di.navigation.AndroidAccessibilityLauncher
import com.github.zharovvv.common.di.meta.FeatureApi

interface AndroidAccessibilityApi : FeatureApi {

    val router: AndroidAccessibilityLauncher
}
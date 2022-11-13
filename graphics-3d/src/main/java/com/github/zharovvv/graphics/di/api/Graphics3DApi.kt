package com.github.zharovvv.graphics.di.api

import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.graphics.di.navigation.Graphics3DLauncher

interface Graphics3DApi : FeatureApi {

    val router: Graphics3DLauncher
}
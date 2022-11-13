package com.github.zharovvv.compose.sandbox.di.api

import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.compose.sandbox.di.api.navigation.ComposeSandboxLauncher

interface ComposeSandboxApi : FeatureApi {

    val router: ComposeSandboxLauncher
}
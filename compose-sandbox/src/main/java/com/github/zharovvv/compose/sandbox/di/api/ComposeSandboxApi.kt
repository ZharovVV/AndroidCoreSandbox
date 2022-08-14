package com.github.zharovvv.compose.sandbox.di.api

import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.compose.sandbox.di.api.navigation.ComposeSandboxRouter

interface ComposeSandboxApi : FeatureApi {

    val router: ComposeSandboxRouter
}
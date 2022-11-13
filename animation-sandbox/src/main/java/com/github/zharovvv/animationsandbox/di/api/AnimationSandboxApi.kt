package com.github.zharovvv.animationsandbox.di.api

import com.github.zharovvv.animationsandbox.di.navigation.AnimationSandboxLauncher
import com.github.zharovvv.common.di.meta.FeatureApi

interface AnimationSandboxApi : FeatureApi {

    val router: AnimationSandboxLauncher
}
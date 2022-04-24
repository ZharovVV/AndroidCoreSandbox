package com.github.zharovvv.animationsandbox.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import dagger.Component

@Component(
    modules = [
        AnimationSandboxModule::class
    ]
)
@PerFeature
interface AnimationSandboxComponent : AnimationSandboxApi {
}
package com.github.zharovvv.compose.sandbox.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.compose.sandbox.di.api.internal.ComposeSandboxInternalApi
import dagger.Component

@Component(
    modules = [
        ComposeSandboxModule::class
    ]
)
@PerFeature
internal interface ComposeSandboxComponent : ComposeSandboxInternalApi
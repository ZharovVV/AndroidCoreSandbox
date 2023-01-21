package com.github.zharovvv.compose.sandbox.di.api

import com.github.zharovvv.compose.sandbox.di.api.internal.ComposeSandboxInternalModule
import dagger.Module

@Module(
    includes = [
        ComposeSandboxInternalModule::class
    ]
)
class ComposeSandboxModule {

}

package com.github.zharovvv.compose.sandbox.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.compose.sandbox.di.api.internal.ComposeSandboxInternalModule
import com.github.zharovvv.compose.sandbox.di.api.navigation.ComposeSandboxRouter
import com.github.zharovvv.compose.sandbox.di.api.navigation.ComposeSandboxRouterImpl
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        ComposeSandboxInternalModule::class
    ]
)
class ComposeSandboxModule {

    @PerFeature
    @Provides
    fun provideComposeSandboxRouter(): ComposeSandboxRouter {
        return ComposeSandboxRouterImpl()
    }
}

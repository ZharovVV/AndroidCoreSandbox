package com.github.zharovvv.compose.sandbox.di.api.internal

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.compose.sandbox.di.api.internal.ui.ComposeSandboxUiModule
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        ComposeSandboxUiModule::class
    ]
)
internal class ComposeSandboxInternalModule {

    //пример инкапсуляции на уровне модуля
    @PerFeature
    @Provides
    fun provideInternalProperty(): String = "secret module property"
}
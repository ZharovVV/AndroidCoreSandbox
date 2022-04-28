package com.github.zharovvv.compose.sandbox.di.api.internal

import com.github.zharovvv.common.di.scope.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class ComposeSandboxInternalModule {

    @PerFeature
    @Provides
    fun provideInternalProperty(): String = "secret module property"
}
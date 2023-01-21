package com.github.zharovvv.graphics.di.api

import com.github.zharovvv.graphics.di.internal.Graphics3DInternalModule
import dagger.Module

@Module(
    includes = [
        Graphics3DInternalModule::class
    ]
)
class Graphics3DModule {
}
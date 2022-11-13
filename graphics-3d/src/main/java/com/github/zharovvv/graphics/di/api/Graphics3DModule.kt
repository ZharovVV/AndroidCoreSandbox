package com.github.zharovvv.graphics.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.graphics.di.internal.Graphics3DInternalModule
import com.github.zharovvv.graphics.di.navigation.Graphics3DLauncher
import com.github.zharovvv.graphics.di.navigation.Graphics3DLauncherImpl
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        Graphics3DInternalModule::class
    ]
)
class Graphics3DModule {

    @PerFeature
    @Provides
    fun router(): Graphics3DLauncher = Graphics3DLauncherImpl()
}
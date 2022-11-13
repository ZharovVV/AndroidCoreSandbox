package com.github.zharovvv.rxjavasandbox.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.rxjavasandbox.di.navigation.RxJavaSandboxLauncher
import com.github.zharovvv.rxjavasandbox.di.navigation.RxJavaSandboxLauncherImpl
import dagger.Module
import dagger.Provides

@Module
class RxJavaSandboxModule {

    @PerFeature
    @Provides
    fun provideRxJavaSandboxRouter(): RxJavaSandboxLauncher {
        return RxJavaSandboxLauncherImpl()
    }
}

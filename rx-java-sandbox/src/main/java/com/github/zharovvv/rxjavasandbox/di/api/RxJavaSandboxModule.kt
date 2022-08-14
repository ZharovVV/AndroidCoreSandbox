package com.github.zharovvv.rxjavasandbox.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.rxjavasandbox.di.navigation.RxJavaSandboxRouter
import com.github.zharovvv.rxjavasandbox.di.navigation.RxJavaSandboxRouterImpl
import dagger.Module
import dagger.Provides

@Module
class RxJavaSandboxModule {

    @PerFeature
    @Provides
    fun provideRxJavaSandboxRouter(): RxJavaSandboxRouter {
        return RxJavaSandboxRouterImpl()
    }
}

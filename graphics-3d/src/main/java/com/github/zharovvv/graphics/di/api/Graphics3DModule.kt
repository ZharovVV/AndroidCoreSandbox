package com.github.zharovvv.graphics.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.graphics.di.navigation.Graphics3DRouter
import com.github.zharovvv.graphics.di.navigation.Graphics3DRouterImpl
import dagger.Module
import dagger.Provides

@Module
class Graphics3DModule {

    @PerFeature
    @Provides
    fun router(): Graphics3DRouter = Graphics3DRouterImpl()
}
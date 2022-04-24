package com.github.zharovvv.animationsandbox.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import dagger.Module
import dagger.Provides

@Module
class AnimationSandboxModule {

    @PerFeature
    @Provides
    fun provideAnimationSandboxRouter(): AnimationSandboxRouter {
        return AnimationSandboxRouterImpl()
    }
}

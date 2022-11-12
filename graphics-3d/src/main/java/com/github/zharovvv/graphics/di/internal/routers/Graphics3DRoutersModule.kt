package com.github.zharovvv.graphics.di.internal.routers

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.graphics.presentation.fragments.FragmentRouter
import com.github.zharovvv.graphics.presentation.fragments.OpenGLFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal class Graphics3DRoutersModule {

    @PerFeature
    @Provides
    @Named(OpenGLFragment.TAG)
    fun openGLFragmentRouter(): FragmentRouter =
        FragmentRouter(
            fragmentClass = OpenGLFragment::class,
            fragmentTag = OpenGLFragment.TAG
        )
}
package com.github.zharovvv.graphics.di.internal

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.graphics.R
import com.github.zharovvv.graphics.di.internal.routers.Graphics3DRoutersModule
import com.github.zharovvv.graphics.presentation.fragments.FragmentRouter
import com.github.zharovvv.graphics.presentation.fragments.OpenGLFragment
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Named

@Module(
    includes = [
        Graphics3DRoutersModule::class
    ]
)
internal class Graphics3DInternalModule {

    @PerFeature
    @Provides
    fun internalEntryPoints(entryPointsSet: Set<@JvmSuppressWildcards EntryPoint>): List<EntryPoint> =
        entryPointsSet.toList()

    @PerFeature
    @Provides
    @IntoSet
    fun openGLEntryPoint(
        @Named(OpenGLFragment.TAG) fragmentRouter: FragmentRouter
    ): EntryPoint = EntryPoint(
        name = "OpenGL",
        description = "Песочница для работы с OpenGL",
        iconResId = R.drawable.ic_opengl_logo_24,
        routerProvider = { fragmentRouter }
    )
}
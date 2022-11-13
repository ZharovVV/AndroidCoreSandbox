package com.github.zharovvv.graphics.di.internal.routers

import androidx.fragment.app.Fragment
import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.core.navigation.FragmentLauncher
import com.github.zharovvv.graphics.presentation.fragments.OpenGLFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named
import kotlin.reflect.KClass

@Module
internal class Graphics3DLaunchersModule {

    @PerFeature
    @Provides
    @Named(OpenGLFragment.TAG)
    fun openGLFragmentLauncher(): FragmentLauncher =
        object : FragmentLauncher {
            override val fragmentClass: KClass<out Fragment> = OpenGLFragment::class
            override val fragmentTag: String = OpenGLFragment.TAG
        }
}
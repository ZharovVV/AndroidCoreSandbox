package com.github.zharovvv.graphics.di.internal.ui

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.zharovvv.common.di.internalFeatureApi
import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.graphics.di.api.Graphics3DApi
import com.github.zharovvv.graphics.di.internal.Graphics3DInternalApi
import com.github.zharovvv.graphics.opengl.shader.ShaderSourceLoader
import com.github.zharovvv.graphics.presentation.fragments.OpenGLViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
class Graphics3DUiModule {

    @Provides
    @[IntoMap ViewModelKey(OpenGLViewModel::class)]
    fun openGLViewModel(
        shaderSourceLoader: ShaderSourceLoader
    ): ViewModel = OpenGLViewModel(shaderSourceLoader)

    @PerFeature
    @Provides
    fun multiViewModelFactory(
        viewModelFactoriesMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): MultiViewModelFactory = MultiViewModelFactory(viewModelFactoriesMap)
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

class MultiViewModelFactory(
    private val viewModelFactoriesMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    //Используем здесь именно Provider так как каждый раз нам нужен новый инстанс ViewModel-и.
) : ViewModelProvider.Factory {

    val viewModelClasses: Set<Class<out ViewModel>> get() = viewModelFactoriesMap.keys

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelFactoriesMap.getValue(modelClass as Class<ViewModel>).get() as T
    }
}

internal inline fun <reified VM : ViewModel> ComponentActivity.diViewModels(): Lazy<VM> =
    viewModels {
        val internalApi: Graphics3DInternalApi = internalFeatureApi()
        internalApi.multiViewModelFactory
    }

internal inline fun <reified VM : ViewModel> Fragment.diViewModels(): Lazy<VM> =
    viewModels {
        val internalApi: Graphics3DInternalApi =
            internalFeatureApi<Graphics3DApi, Graphics3DInternalApi>()
        internalApi.multiViewModelFactory
    }
package com.github.zharovvv.compose.sandbox.di.api.internal.ui

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.zharovvv.common.di.internalFeatureApi
import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.compose.sandbox.di.api.internal.ComposeSandboxInternalApi
import com.github.zharovvv.compose.sandbox.ui.ComposeMainViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import java.io.Closeable
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
internal class ComposeSandboxUiModule {

    //сюда добавляем другие ViewModel-и

    @Provides
    @[IntoMap ViewModelKey(ComposeMainViewModel::class)]
    fun composeMainViewModel(
        scopeProvider: Provider<ViewModelScope>
    ): ViewModel = ComposeMainViewModel(scopeProvider.get())

    @PerFeature
    @Provides
    fun multiViewModelFactory(
        viewModelFactoriesMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): MultiViewModelFactory = MultiViewModelFactory(viewModelFactoriesMap)

    @Provides
    fun viewModelScope(): ViewModelScope = ViewModelScope()
}

//-----------------------------------------------------
//TODO все, что ниже можно вынести в отдельный gradle-модуль
//Назвать как-нибудь core-di-android

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

class ViewModelScope(
    private val scope: CoroutineScope = MainScope()
) : CoroutineScope by scope, Closeable {
    override fun close() = scope.cancel()
}

//Можно тоже как-нибудь обобщить
internal inline fun <reified VM : ViewModel> ComponentActivity.diViewModels(): Lazy<VM> =
    viewModels {
        val internalApi: ComposeSandboxInternalApi = internalFeatureApi()
        internalApi.multiViewModelFactory
    }
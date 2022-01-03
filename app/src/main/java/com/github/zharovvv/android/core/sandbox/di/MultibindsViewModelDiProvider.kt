package com.github.zharovvv.android.core.sandbox.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class MainViewModel @Inject constructor() : ViewModel()
class DetailsViewModel @Inject constructor() : ViewModel()
class SecondViewModel @Inject constructor() : ViewModel()

class MultiViewModelFactory
@Inject constructor(
    private val viewModelFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    //Используем здесь именно Provider так как каждый раз нам нужен новый инстанс ViewModel-и.
) : ViewModelProvider.Factory {

    val viewModelClasses: Set<Class<*>> get() = viewModelFactories.keys

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelFactories.getValue(modelClass as Class<ViewModel>).get() as T
    }
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
interface ViewModelsModule {

    @Binds
//    @[IntoMap ClassKey(MainViewModel::class)]
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
//    @[IntoMap ClassKey(DetailsViewModel::class)]
    @[IntoMap ViewModelKey(DetailsViewModel::class)]
    fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(SecondViewModel::class)]
    fun bindSecondViewModel(secondViewModel: SecondViewModel): ViewModel
}


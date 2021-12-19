package com.github.zharovvv.android.core.sandbox.di.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.zharovvv.android.core.sandbox.di.MainThread
import com.github.zharovvv.android.core.sandbox.di.example.providers.SchedulerProvider
import com.github.zharovvv.android.core.sandbox.di.example.repository.ExampleRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class DaggerExampleViewModel(
    private val initId: String,
    private val exampleRepository: ExampleRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data
    private val compositeDisposable = CompositeDisposable()

    fun requestData() {
        compositeDisposable += exampleRepository.getData(initId = initId)
            .observeOn(schedulerProvider.provide())
            .subscribe { _data.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    /**
     * AssistedInject - используется когда, не все параметры, необходимые для создания объекта,
     * находятся в графе зависимостей
     * (например какие-нибудь айдишники, которые будут известны только в рантайме).
     * Параметры, которые не находятся в графе зависимостей, должны быть помечены аннотацией Assisted.
     * Имя для Assisted-параметров требуется задавать только если у вас их несколько одного типа.
     * Также требуется написать Фабрику (которая должна быть помечена аннотацией AssistedFactory)
     * для создания объекта с AssistedInject-конструктором.
     */
    class Factory @AssistedInject constructor(
        @Assisted("initId") private val initId: String,
        private val exampleRepository: ExampleRepository,
//        @param:Named("mainThread")
        @MainThread
        private val schedulerProvider: SchedulerProvider
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == DaggerExampleViewModel::class.java)
            return DaggerExampleViewModel(initId, exampleRepository, schedulerProvider) as T
        }

        @AssistedFactory
        interface DiFactory {

            fun create(@Assisted("initId") initId: String): Factory
        }
    }

    //Пример реализации фабрики без @AssistedInject
    //class Factory(
    //        private val initId: String,
    //        private val exampleRepository: ExampleRepository,
    //        private val schedulerProvider: SchedulerProvider
    //    ) : ViewModelProvider.Factory {
    //
    //        @Suppress("UNCHECKED_CAST")
    //        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    //            require(modelClass == DaggerExampleViewModel::class)
    //            return DaggerExampleViewModel(initId, exampleRepository, schedulerProvider) as T
    //        }
    //
    //        class DiFactory
    //        @Inject constructor(
    //            private val exampleRepository: ExampleRepository,
    //            @Named("mainThread")
    //            private val schedulerProvider: SchedulerProvider
    //        ) {
    //
    //            fun create(initId: String): Factory {
    //                return Factory(initId, exampleRepository, schedulerProvider)
    //            }
    //        }
    //    }
}
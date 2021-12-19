package com.github.zharovvv.android.core.sandbox.di.example.repository

import com.github.zharovvv.android.core.sandbox.di.Io
import com.github.zharovvv.android.core.sandbox.di.example.network.NetworkServiceExample
import com.github.zharovvv.android.core.sandbox.di.example.providers.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

interface ExampleRepository {
    fun getData(initId: String): Observable<String>
}

class ExampleRepositoryImpl
@Inject constructor(
    private val networkServiceExample: NetworkServiceExample,
//    @Named("io")
    @Io
    private val schedulerProvider: SchedulerProvider
) : ExampleRepository {

    override fun getData(initId: String): Observable<String> {
        return Observable.fromCallable {
            networkServiceExample.requestData(initId = initId)
        }.subscribeOn(schedulerProvider.provide())
    }
}


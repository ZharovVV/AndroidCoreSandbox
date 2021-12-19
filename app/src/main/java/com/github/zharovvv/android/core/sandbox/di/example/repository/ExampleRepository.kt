package com.github.zharovvv.android.core.sandbox.di.example.repository

import com.github.zharovvv.android.core.sandbox.di.example.network.NetworkServiceExample
import com.github.zharovvv.android.core.sandbox.di.example.providers.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

interface ExampleRepository {
    fun getData(initId: String): Observable<String>
}

class ExampleRepositoryImpl
@Inject constructor(
    private val networkServiceExample: NetworkServiceExample,
    @Named("io")
    private val schedulerProvider: SchedulerProvider
) : ExampleRepository {

    override fun getData(initId: String): Observable<String> {
        return Observable.fromCallable {
            networkServiceExample.requestData(initId = initId)
        }.subscribeOn(schedulerProvider.provide())
    }
}


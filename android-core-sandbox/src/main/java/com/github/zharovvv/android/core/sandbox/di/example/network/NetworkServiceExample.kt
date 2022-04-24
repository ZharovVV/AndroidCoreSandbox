package com.github.zharovvv.android.core.sandbox.di.example.network

import com.github.zharovvv.android.core.sandbox.di.AppScope
import javax.inject.Inject

interface NetworkServiceExample {
    fun requestData(initId: String): String
}

//@Singleton
@AppScope
class NetworkServiceExampleImpl
@Inject constructor() : NetworkServiceExample {

    override fun requestData(initId: String): String {
        try {
            Thread.sleep(3000L)
        } catch (e: Exception) {
            //io.reactivex.exceptions.UndeliverableException: The exception could not be delivered to the consumer because it has already canceled/disposed the flow or the exception has nowhere to go to begin with.
        }
        return "Data from Network! Initial id: $initId"
    }
}
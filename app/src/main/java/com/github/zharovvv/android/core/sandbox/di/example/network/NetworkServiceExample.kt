package com.github.zharovvv.android.core.sandbox.di.example.network

import javax.inject.Inject

interface NetworkServiceExample {
    fun requestData(): String
}

class NetworkServiceExampleImpl
@Inject constructor() : NetworkServiceExample {

    override fun requestData(): String {
        Thread.sleep(1000L)
        return "Data from Network!"
    }
}
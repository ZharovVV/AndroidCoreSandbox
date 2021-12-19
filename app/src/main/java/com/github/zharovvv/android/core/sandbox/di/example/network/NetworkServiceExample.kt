package com.github.zharovvv.android.core.sandbox.di.example.network

import javax.inject.Inject

interface NetworkServiceExample {
    fun requestData(initId: String): String
}

class NetworkServiceExampleImpl
@Inject constructor() : NetworkServiceExample {

    override fun requestData(initId: String): String {
        Thread.sleep(1000L)
        return "Data from Network! Initial id: $initId"
    }
}
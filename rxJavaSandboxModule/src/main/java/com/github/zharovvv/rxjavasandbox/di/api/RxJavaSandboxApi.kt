package com.github.zharovvv.rxjavasandbox.di.api

import com.github.zharovvv.common.di.meta.FeatureApi

interface RxJavaSandboxApi : FeatureApi {

    val router: RxJavaSandboxRouter
}
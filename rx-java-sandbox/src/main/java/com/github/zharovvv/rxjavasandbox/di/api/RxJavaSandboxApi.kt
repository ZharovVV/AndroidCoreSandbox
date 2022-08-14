package com.github.zharovvv.rxjavasandbox.di.api

import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.rxjavasandbox.di.navigation.RxJavaSandboxRouter

interface RxJavaSandboxApi : FeatureApi {

    val router: RxJavaSandboxRouter
}
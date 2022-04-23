package com.github.zharovvv.rxjavasandbox.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import dagger.Component

@Component(
    modules = [
        RxJavaSandboxModule::class
    ]
)
@PerFeature
interface RxJavaSandboxComponent : RxJavaSandboxApi {

}
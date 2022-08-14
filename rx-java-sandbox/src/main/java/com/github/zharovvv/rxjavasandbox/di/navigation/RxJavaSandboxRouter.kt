package com.github.zharovvv.rxjavasandbox.di.navigation

import com.github.zharovvv.common.di.meta.ProvidedBy
import com.github.zharovvv.core.navigation.Router
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi

@ProvidedBy(RxJavaSandboxApi::class)
interface RxJavaSandboxRouter : Router
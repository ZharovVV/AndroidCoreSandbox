package com.github.zharovvv.compose.sandbox.di.api.internal

import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi

internal interface ComposeSandboxInternalApi : ComposeSandboxApi {

    val internalProperty: String
}
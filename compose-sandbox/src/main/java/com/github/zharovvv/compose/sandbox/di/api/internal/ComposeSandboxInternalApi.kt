package com.github.zharovvv.compose.sandbox.di.api.internal

import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.compose.sandbox.di.api.internal.ui.MultiViewModelFactory

internal interface ComposeSandboxInternalApi : ComposeSandboxApi {

    val internalProperty: String

    val multiViewModelFactory: MultiViewModelFactory
}
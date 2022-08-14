package com.github.zharovvv.compose.sandbox.di.api.navigation

import com.github.zharovvv.common.di.meta.ProvidedBy
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.core.navigation.Router

@ProvidedBy(ComposeSandboxApi::class)
interface ComposeSandboxRouter : Router
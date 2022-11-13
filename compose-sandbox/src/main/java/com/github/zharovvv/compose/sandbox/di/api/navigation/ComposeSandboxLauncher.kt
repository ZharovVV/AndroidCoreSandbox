package com.github.zharovvv.compose.sandbox.di.api.navigation

import com.github.zharovvv.common.di.meta.ProvidedBy
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.core.navigation.ActivityLauncher

@ProvidedBy(ComposeSandboxApi::class)
interface ComposeSandboxLauncher : ActivityLauncher
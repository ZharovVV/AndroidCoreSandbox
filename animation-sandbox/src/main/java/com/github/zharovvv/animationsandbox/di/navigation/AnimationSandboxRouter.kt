package com.github.zharovvv.animationsandbox.di.navigation

import com.github.zharovvv.animationsandbox.di.api.AnimationSandboxApi
import com.github.zharovvv.common.di.meta.ProvidedBy
import com.github.zharovvv.core.navigation.Router

@ProvidedBy(AnimationSandboxApi::class)
interface AnimationSandboxRouter : Router
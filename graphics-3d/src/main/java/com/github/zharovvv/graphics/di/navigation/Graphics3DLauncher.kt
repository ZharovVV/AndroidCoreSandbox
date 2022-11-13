package com.github.zharovvv.graphics.di.navigation

import com.github.zharovvv.common.di.meta.ProvidedBy
import com.github.zharovvv.core.navigation.ActivityLauncher
import com.github.zharovvv.graphics.di.api.Graphics3DApi

@ProvidedBy(Graphics3DApi::class)
interface Graphics3DLauncher : ActivityLauncher
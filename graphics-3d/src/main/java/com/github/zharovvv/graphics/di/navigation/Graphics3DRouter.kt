package com.github.zharovvv.graphics.di.navigation

import com.github.zharovvv.common.di.meta.ProvidedBy
import com.github.zharovvv.core.navigation.Router
import com.github.zharovvv.graphics.di.api.Graphics3DApi

@ProvidedBy(Graphics3DApi::class)
interface Graphics3DRouter : Router
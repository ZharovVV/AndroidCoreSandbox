package com.github.zharovvv.graphics.di.internal

import com.github.zharovvv.core.navigation.EntryPoint.FragmentEntryPoint
import com.github.zharovvv.graphics.di.api.Graphics3DApi

internal interface Graphics3DInternalApi : Graphics3DApi {

    val internalEntryPoints: List<FragmentEntryPoint>
}
package com.github.zharovvv.graphics.di.internal

import com.github.zharovvv.core.navigation.EntryPoint.FragmentEntryPoint
import com.github.zharovvv.graphics.di.api.Graphics3DApi
import com.github.zharovvv.graphics.di.internal.ui.MultiViewModelFactory
import com.github.zharovvv.graphics.opengl.OpenGLEngine

internal interface Graphics3DInternalApi : Graphics3DApi {

    val internalEntryPoints: List<FragmentEntryPoint>

    val multiViewModelFactory: MultiViewModelFactory

    val openGLEngine: OpenGLEngine
}
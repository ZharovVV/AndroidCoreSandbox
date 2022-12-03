package com.github.zharovvv.graphics.opengl

import android.opengl.GLSurfaceView

interface RendererWrapper : GLSurfaceView.Renderer {

    fun addOnFailRenderListener(action: () -> Unit)
}
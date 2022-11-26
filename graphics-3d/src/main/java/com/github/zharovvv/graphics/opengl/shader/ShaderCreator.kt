package com.github.zharovvv.graphics.opengl.shader

import androidx.annotation.RawRes

interface ShaderCreator {
    fun create(type: Int, @RawRes shaderResId: Int): Shader
    fun create(type: Int, shaderUrl: String): Shader
}
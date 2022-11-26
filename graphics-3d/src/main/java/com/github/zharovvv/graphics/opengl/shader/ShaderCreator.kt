package com.github.zharovvv.graphics.opengl.shader

import com.github.zharovvv.graphics.opengl.shader.model.Shader
import com.github.zharovvv.graphics.opengl.shader.model.ShaderCreatingException
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource

interface ShaderCreator {

    @Throws(ShaderCreatingException::class)
    fun create(shaderSource: ShaderSource): Shader
}
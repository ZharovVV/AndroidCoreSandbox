package com.github.zharovvv.graphics.opengl

import com.github.zharovvv.graphics.opengl.program.model.Program
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource

interface OpenGLEngine {

    fun createProgram(shaderSources: List<ShaderSource>): Program
}
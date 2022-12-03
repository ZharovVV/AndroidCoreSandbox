package com.github.zharovvv.graphics.opengl

import com.github.zharovvv.graphics.opengl.program.model.Program
import com.github.zharovvv.graphics.opengl.program.model.ProgramCreatingException
import com.github.zharovvv.graphics.opengl.shader.model.ShaderCreatingException
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource

interface OpenGLEngine {

    @Throws(ProgramCreatingException::class, ShaderCreatingException::class)
    fun createProgram(shaderSources: List<ShaderSource>): Program
}
package com.github.zharovvv.graphics.opengl

import com.github.zharovvv.graphics.opengl.program.ProgramCreator
import com.github.zharovvv.graphics.opengl.program.model.Program
import com.github.zharovvv.graphics.opengl.shader.ShaderCreator
import com.github.zharovvv.graphics.opengl.shader.model.Shader
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource

class OpenGLEngineImpl(
    private val programCreator: ProgramCreator,
    private val shaderCreator: ShaderCreator
) : OpenGLEngine {

    override fun createProgram(shaderSources: List<ShaderSource>): Program {
        val shaders: List<Shader> = shaderSources.map(shaderCreator::create)
        return programCreator.create(shaders)
    }
}
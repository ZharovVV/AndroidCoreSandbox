package com.github.zharovvv.graphics.opengl.program

import com.github.zharovvv.graphics.opengl.program.model.Program
import com.github.zharovvv.graphics.opengl.program.model.ProgramCreatingException
import com.github.zharovvv.graphics.opengl.shader.model.Shader

interface ProgramCreator {

    @Throws(ProgramCreatingException::class)
    fun create(shaders: List<Shader>): Program
}
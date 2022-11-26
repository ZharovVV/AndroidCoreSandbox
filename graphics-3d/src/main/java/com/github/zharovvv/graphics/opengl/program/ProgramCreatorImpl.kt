package com.github.zharovvv.graphics.opengl.program

import android.opengl.GLES20.*
import android.util.Log
import com.github.zharovvv.graphics.opengl.DebugConstants.LOG_TAG
import com.github.zharovvv.graphics.opengl.program.model.Program
import com.github.zharovvv.graphics.opengl.program.model.ProgramCreatingException
import com.github.zharovvv.graphics.opengl.shader.model.Shader
import kotlin.system.measureTimeMillis

class ProgramCreatorImpl : ProgramCreator {

    override fun create(shaders: List<Shader>): Program {
        val programId: Int
        val duration = measureTimeMillis {
            programId = glCreateProgram()
            if (programId == 0) throw ProgramCreatingException("programId is 0.")
            shaders.forEach { shader ->
                glAttachShader(programId, shader.id)
            }
            glLinkProgram(programId)
            val linkStatus = IntArray(size = 1)
            glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0)
            if (linkStatus[0] == 0) {
                glDeleteProgram(programId)
                throw ProgramCreatingException("Incorrect program link status!")
            }
        }
        Log.d(LOG_TAG, "Creating program duration = $duration ms.")
        return Program(programId)
    }
}
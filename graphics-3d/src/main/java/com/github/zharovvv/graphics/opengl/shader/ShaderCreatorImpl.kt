package com.github.zharovvv.graphics.opengl.shader

import android.opengl.GLES20.*
import android.util.Log
import com.github.zharovvv.graphics.opengl.DebugConstants.LOG_TAG
import com.github.zharovvv.graphics.opengl.shader.model.Shader
import com.github.zharovvv.graphics.opengl.shader.model.ShaderCreatingException
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource
import kotlin.system.measureTimeMillis

internal class ShaderCreatorImpl : ShaderCreator {

    override fun create(shaderSource: ShaderSource): Shader {
        val shaderId: Int
        val duration = measureTimeMillis {
            shaderId = glCreateShader(shaderSource.type)
            if (shaderId == 0) throw ShaderCreatingException("shaderId == 0.")
            glShaderSource(shaderId, shaderSource.source)
            glCompileShader(shaderId)
            val compileStatus = IntArray(size = 1)
            glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0)
            if (compileStatus[0] == 0) {
                glDeleteShader(shaderId)
                throw ShaderCreatingException("Incorrect shader compile status!")
            }
        }
        Log.d(LOG_TAG, "Creating shader duration = $duration ms.")
        return Shader(id = shaderId, type = shaderSource.type)
    }
}
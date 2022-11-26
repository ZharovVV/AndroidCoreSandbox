package com.github.zharovvv.graphics.opengl

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(
    private val openGLEngine: OpenGLEngine,
    private val shaderSources: List<ShaderSource>
) : GLSurfaceView.Renderer {

    private val vertexData: FloatBuffer

    init {
        val vertices = floatArrayOf(-0.5f, -0.2f, 0.0f, 0.2f, 0.5f, -0.2f)
        vertexData = ByteBuffer.allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        vertexData.put(vertices)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        try {
            glClearColor(0f, 0f, 0f, 1f)
            val program = openGLEngine.createProgram(shaderSources)
            glUseProgram(program.id)
            bindData(program.id)
        } catch (e: RuntimeException) {
            Log.e(DebugConstants.LOG_TAG, e.stackTraceToString())
        }
    }

    private fun bindData(programId: Int) {
        val uColorLocation = glGetUniformLocation(programId, "u_Color")
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        val aPositionLocation = glGetAttribLocation(programId, "a_Position")
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData)
        glEnableVertexAttribArray(aPositionLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
        glDrawArrays(GL_TRIANGLES, 0, 3)
    }
}
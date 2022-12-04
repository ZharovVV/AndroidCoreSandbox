package com.github.zharovvv.graphics.opengl

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

abstract class BaseRenderer(
    private val openGLEngine: OpenGLEngine,
    private val shaderSources: List<ShaderSource>,
    private val depthEnabled: Boolean = true
) : GLSurfaceView.Renderer {

    private val handler = Handler(Looper.getMainLooper())
    private var _failRenderAction: (() -> Unit)? = null

    final override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        try {
            glClearColor(0f, 0f, 0f, 1f)
            if (depthEnabled) {
                glEnable(GL_DEPTH_TEST) //строка включает использование буфера глубины
            }
            val program = openGLEngine.createProgram(shaderSources)
            glUseProgram(program.id)
            bindData(program.id)
        } catch (e: RuntimeException) {
            Log.e(DebugConstants.LOG_TAG, e.stackTraceToString())
            val failRenderAction = _failRenderAction
            if (failRenderAction != null) {
                handler.post(failRenderAction)
            } else {
                throw e
            }
        }
    }

    protected abstract fun bindData(programId: Int)

    fun addOnFailRenderListener(action: () -> Unit) {
        _failRenderAction = action
    }

    fun floatBuffer(vararg elements: Float): FloatBuffer = elements
        .let { source: FloatArray ->
            ByteBuffer.allocateDirect(source.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(source)
                .position(0) as FloatBuffer
        }
}
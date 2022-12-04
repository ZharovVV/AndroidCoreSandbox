package com.github.zharovvv.graphics.opengl

import android.opengl.GLES20.*
import android.opengl.Matrix
import android.os.SystemClock
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos


class CameraRenderer(
    openGLEngine: OpenGLEngine,
    shaderSources: List<ShaderSource>
) : BaseRenderer(openGLEngine, shaderSources) {

    private val projectionMatrix = FloatArray(size = 16)

    //Camera
    private val viewMatrix = FloatArray(size = 16)
    private val resultMatrix = FloatArray(size = 16)

    private val s = 0.4f
    private val d = 0.9f
    private val l = 3f
    private val positionFloatBuffer: FloatBuffer = floatBuffer(
        // первый треугольник
        -2 * s, -s, d,
        2 * s, -s, d,
        0f, s, d,

        // второй треугольник
        -2 * s, -s, -d,
        2 * s, -s, -d,
        0f, s, -d,

        // третий треугольник
        d, -s, -2 * s,
        d, -s, 2 * s,
        d, s, 0f,

        // четвертый треугольник
        -d, -s, -2 * s,
        -d, -s, 2 * s,
        -d, s, 0f,

        // ось X
        -l, 0f, 0f,
        l, 0f, 0f,

        // ось Y
        0f, -l, 0f,
        0f, l, 0f,

        // ось Z
        0f, 0f, -l,
        0f, 0f, l,
    )
    private var uColorLocation: Int = 0
    private var uMatrixLocation: Int = 0

    override fun bindData(programId: Int) {
        val aPositionLocation = glGetAttribLocation(programId, "a_Position")
        glVertexAttribPointer(aPositionLocation, 3, GL_FLOAT, false, 0, positionFloatBuffer)
        glEnableVertexAttribArray(aPositionLocation)
        uColorLocation = glGetUniformLocation(programId, "u_Color")
        uMatrixLocation = glGetUniformLocation(programId, "u_Matrix")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        projectionMatrix.fillProjectionMatrix(width, height)
    }

    private fun FloatArray.fillProjectionMatrix(width: Int, height: Int) {
        //координаты near-границы
        var left = -1f
        var right = 1f
        var bottom = -1f
        var top = 1f
        //расстояние от камеры до near-границы
        val near = 2f
        //расстояние от камеры до far-границы
        val far = 8f

        val ratio: Float
        if (width > height) {
            ratio = width.toFloat() / height
            left *= ratio
            right *= ratio
        } else {
            ratio = height.toFloat() / width
            bottom *= ratio
            top *= ratio
        }
        Matrix.frustumM(this, 0, left, right, bottom, top, near, far)
    }

    private fun FloatArray.fillViewMatrix() {
        val time: Float = (SystemClock.uptimeMillis() % TIME).toFloat() / TIME
        val angle = time * 2 * 3.1415925f
        // точка положения камеры
        val eyeX = (cos(angle.toDouble()) * 4f).toFloat()
        val eyeY = 1f
        val eyeZ = 4f
        // точка направления камеры
        val centerX = 0f
        val centerY = 0f
        val centerZ = 0f
        // up-вектор
        val upX = 0f
        val upY = 1f
        val upZ = 0f
        Matrix.setLookAtM(this, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        viewMatrix.fillViewMatrix()
        Matrix.multiplyMM(resultMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        glUniformMatrix4fv(uMatrixLocation, 1, false, resultMatrix, 0)

        // треугольники
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 0, 3)

        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 3, 3)

        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 6, 3)

        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 9, 3)

        // оси
        glLineWidth(1f)

        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_LINES, 12, 2)

        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f)
        glDrawArrays(GL_LINES, 14, 2)

        glUniform4f(uColorLocation, 0.0f, 0.5f, 1.0f, 1.0f)
        glDrawArrays(GL_LINES, 16, 2)
    }

    companion object {
        const val TIME = 10000L
    }
}
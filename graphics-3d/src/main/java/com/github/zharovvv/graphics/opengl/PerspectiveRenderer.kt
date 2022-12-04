package com.github.zharovvv.graphics.opengl

import android.opengl.GLES20.*
import android.opengl.Matrix
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class PerspectiveRenderer(
    openGLEngine: OpenGLEngine,
    shaderSources: List<ShaderSource>
) : BaseRenderer(openGLEngine, shaderSources) {

    private val z1 = -1f
    private val z2 = -2.99f
    private val positionFloatBuffer: FloatBuffer = floatArrayOf(
        // первый треугольник
        -0.7f, -0.5f, z1,
        0.3f, -0.5f, z1,
        -0.2f, 0.3f, z1,

        // второй треугольник
        -0.3f, -0.4f, z2,
        0.7f, -0.4f, z2,
        0.2f, 0.4f, z2,
    ).let { source ->
        ByteBuffer.allocateDirect(source.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(source)
            .position(0) as FloatBuffer
    }
    private var uColorLocation: Int = 0
    private var uMatrixLocation: Int = 0
    private var _programId: Int = 0

    override fun bindData(programId: Int) {
        val aPositionLocation = glGetAttribLocation(programId, "a_Position")
        glVertexAttribPointer(aPositionLocation, 3, GL_FLOAT, false, 0, positionFloatBuffer)
        glEnableVertexAttribArray(aPositionLocation)
        uColorLocation = glGetUniformLocation(programId, "u_Color")
        uMatrixLocation = glGetUniformLocation(programId, "u_Matrix")
        _programId = programId
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        bindMatrix(_programId, width, height)
    }

    private fun bindMatrix(programId: Int, width: Int, height: Int) {
        //координаты near-границы
        var left = -1f
        var right = 1f
        var bottom = -1f
        var top = 1f
        //расстояние от камеры до near-границы
        val near = 1f
        //расстояние от камеры до far-границы
        val far = 3f

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

        val projectionMatrix = FloatArray(size = 16)
        Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far)
        //Если нам не нужна перспектива (матрица будет описывать не пирамиду, а параллелограмм)
        //то надо использовать orthoM для формирования матрицы.
        glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(
            GL_COLOR_BUFFER_BIT or
                    GL_DEPTH_BUFFER_BIT //GL_DEPTH_BUFFER_BIT - нужно для очистки буфера глубины
        )
        // зеленый треугольник
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 0, 3)
        // синий треугольник
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 3, 3)
    }
}
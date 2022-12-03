package com.github.zharovvv.graphics.opengl

import android.opengl.GLES20.*
import android.util.Log
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class PrimitivesRenderer(
    private val openGLEngine: OpenGLEngine, private val shaderSources: List<ShaderSource>
) : RendererWrapper {

    private var _failRenderAction: (() -> Unit)? = null
    private val positionVertexData: FloatBuffer
    private val colorVertexBuffer: FloatBuffer = floatArrayOf(
        //цвета вершин первого треугольника
        1f, 0f, 0f,
        0f, 1f, 0f,
        0f, 0f, 1f,
        //цвета вершин второго треугольника
        0f, 0f, 1f,
        0f, 1f, 0f,
        1f, 0f, 0f,
        //цвета вершин 3-го треугольника
        1f, 1f, 1f,
        1f, 1f, 0f,
        1f, 0f, 0f,
        //цвета вершин 4-го треугольника
        1f, 0f, 1f,
        1f, 1f, 0f,
        1f, 1f, 1f,
        //цвета вершин линии 1
        1f, 0f, 0f,
        0f, 1f, 0f,
        //цвета вершин линии 2
        0f, 0f, 0f,
        1f, 1f, 1f,
        //цвет точки 1
        0f, 0f, 0f,
        //цвет точки 2
        1f, 0f, 1f,
        //цвет точки 3
        0f, 1f, 0f
    )
        .let { source ->
            ByteBuffer.allocateDirect(source.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(source)
                .position(0) as FloatBuffer
        }
    private var colorLocation: Int = 0

    init {
        val vertices = floatArrayOf(
            // треугольник 1
            -0.9f, 0.8f,
            -0.9f, 0.2f,
            -0.5f, 0.8f,
            // треугольник 2
            -0.6f, 0.2f,
            -0.2f, 0.2f,
            -0.2f, 0.8f,
            // треугольник 3
            0.1f, 0.8f,
            0.1f, 0.2f,
            0.5f, 0.8f,
            // треугольник 4
            0.1f, 0.2f,
            0.5f, 0.2f,
            0.5f, 0.8f,
            // линия 1
            -0.7f, -0.1f,
            0.7f, -0.1f,
            // линия 2
            -0.6f, -0.2f,
            0.6f, -0.2f,
            // точка 1
            -0.5f, -0.3f,
            // точка 2
            0.0f, -0.3f,
            // точка 3
            0.5f, -0.3f,
        )
        positionVertexData =
            ByteBuffer.allocateDirect(vertices.size * 4).order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        positionVertexData.put(vertices)
    }

    override fun addOnFailRenderListener(action: () -> Unit) {
        _failRenderAction = action
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        try {
            glClearColor(1f, 1f, 1f, 0.33f)
            val program = openGLEngine.createProgram(shaderSources)
            glUseProgram(program.id)
            bindData(program.id)
        } catch (e: RuntimeException) {
            Log.e(DebugConstants.LOG_TAG, e.stackTraceToString())
            val failRenderAction = _failRenderAction
            if (failRenderAction != null) {
                failRenderAction.invoke()
            } else {
                throw e
            }
        }
    }

    private fun bindData(programId: Int) {
//        colorLocation = glGetUniformLocation(programId, "u_Color")
        val aPositionLocation = glGetAttribLocation(programId, "a_Position")
        //Методом position сообщаем системе, что данные из vertexData надо будет читать
        // начиная с элемента с индексом 0, т.е. с самого начала.
        positionVertexData.position(0)
        //сообщаем системе, что шейдеру для своего атрибута a_Position необходимо читать данные из массива vertexData.
        glVertexAttribPointer(
            //переменная указывающая на положение атрибута в шейдере. Тут все понятно, используем ранее полученную aPositionLocation, которая знает где сидит a_Position.
            aPositionLocation,
            //указывает системе, сколько элементов буфера vertexData брать для заполнения атрибута a_Position.
            2,  //Указываем именно это значение и таким образом у нас будет три запуска вершинного шейдера:
            // (-0.5, -0.2) - недостающие значения vec4 заполнятся след. образом -> (-0.5, -0.2, 0, 1)
            // (0.0, 0.2)
            // (0.5, -0.2)
            // Значение по умолчанию для vec4 - (0, 0, 0, 1)
            GL_FLOAT, false, 0, positionVertexData
        )
        //Включаем атрибут aPositionLocation
        glEnableVertexAttribArray(aPositionLocation)

        val aColorLocation = glGetAttribLocation(programId, "a_Color")
        glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 0, colorVertexBuffer)
        glEnableVertexAttribArray(aColorLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
        glLineWidth(5f)

        //Синие треугольники
//        glUniform4f(colorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        glDrawArrays(
            //указываем тип графического примитива
            GL_TRIANGLES,
            //указываем, что брать вершины из массива вершин надо начиная с элемента с индексом 0, т.е. с первого элемента массива
            0,
            //кол-во вершин которое необходимо использовать для рисования.
            12
        )

        //зеленая линия
//        glUniform4f(colorLocation, 0f, 1f, 0f, 1f)
        glDrawArrays(GL_LINES, 12, 2)
        //желтая линия
//        glUniform4f(colorLocation, 1f, 1f, 0f, 1f)
        glDrawArrays(GL_LINES, 14, 2)
        //красные точки
//        glUniform4f(colorLocation, 1f, 0f, 0f, 1f)
        glDrawArrays(GL_POINTS, 16, 3)
    }
}
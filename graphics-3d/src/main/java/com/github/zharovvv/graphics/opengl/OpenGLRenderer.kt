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
            glClearColor(1f, 1f, 1f, 0.33f)
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
        //Методом position сообщаем системе, что данные из vertexData надо будет читать
        // начиная с элемента с индексом 0, т.е. с самого начала.
        vertexData.position(0)
        glVertexAttribPointer(
            //переменная указывающая на положение атрибута в шейдере. Тут все понятно, используем ранее полученную aPositionLocation, которая знает где сидит a_Position.
            aPositionLocation,
            //указывает системе, сколько элементов буфера vertexData брать для заполнения атрибута a_Position.
            2,  //Указываем именно это значение и таким образом у нас будет три запуска вершинного шейдера:
            // (-0.5, -0.2) - недостающие значения vec4 заполнятся след. образом -> (-0.5, -0.2, 0, 1)
            // (0.0, 0.2)
            // (0.5, -0.2)
            // Значение по умолчанию для vec4 - (0, 0, 0, 1)
            GL_FLOAT,
            false,
            0,
            vertexData
        )
        //Включаем атрибут aPositionLocation
        glEnableVertexAttribArray(aPositionLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
        glDrawArrays(
            //указываем тип графического примитива
            GL_TRIANGLES,
            //указываем, что брать вершины из массива вершин надо начиная с элемента с индексом 0, т.е. с первого элемента массива
            0,
            //кол-во вершин которое необходимо использовать для рисования.
            3
        )
    }
}
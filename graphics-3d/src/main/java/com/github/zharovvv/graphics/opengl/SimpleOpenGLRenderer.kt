package com.github.zharovvv.graphics.opengl

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 */
class SimpleOpenGLRenderer : GLSurfaceView.Renderer {

    /**
     * Вызывается при создании/пересоздании surface.
     * Т.е. метод будет вызываться при запуске приложения или, например,
     * в уже запущенном приложении при выходе девайса из сна.
     *
     * Здесь будет выполняться установка OpenGL параметров и инициализация графических объектов.
     */
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //устанавливаем дефолтный цвет, который будет отображаться после полной очистки surface.
        //передаем RGBA - компоненты (от 0 до 1)
        glClearColor(0f, 1f, 0f, 1f)
    }

    /**
     * Вызывается при изменении размера surface.
     * Самый распространенный пример - смена ориентации экрана.
     */
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //Задаем область surface, которая будет доступна для вывода изображения.
        // Мы указываем левую нижнюю точку - (0,0) и размеры области - (width, height),
        // т.е.  изображение будет выведено на все surface.
        glViewport(0, 0, width, height)
    }

    /**
     * Вызывается, когда surface готово отобразить очередной кадр.
     * В этом методе мы и будем создавать изображение.
     */
    override fun onDrawFrame(gl: GL10?) {
        //Метод glClear с параметром GL_COLOR_BUFFER_BIT очистит все цвета на экране,
        // и установит цвет, заданный методом glClearColor.
        glClear(GL_COLOR_BUFFER_BIT)
    }
}
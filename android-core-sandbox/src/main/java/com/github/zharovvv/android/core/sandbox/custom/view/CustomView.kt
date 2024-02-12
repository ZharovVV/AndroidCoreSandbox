package com.github.zharovvv.android.core.sandbox.custom.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * # CustomView
 * CustomView - View, которая не находится в Android SDK.
 * Как правило необходимости в создании CustomView - нет.
 * #
 * Жизненный цикл View:
 * Constructor → (parent.addView()) → onAttachedToWindow() → measure() → layout() → (parent.dispatchDraw())
 * → draw()
 * #
 * Следует помнить, что вся отрисовка происходит в px, но оперировать нужно в терминах dp и sp.
 */
class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //constructor

    /**
     * parent calls addView()
     * Говорит о том, что View попала в иерархию родительского layout-а.
     * После того как родитель View вызовет метод addView(View), наш View будет прикреплен к окну
     * (window).
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    /**
     * Вызывается в методе [View.measure].
     *
     * View.measure вызывается после [View.onAttachedToWindow], либо
     * после вызова [View.requestLayout].
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //mode может принимать следующие знаачения
//        MeasureSpec.EXACTLY   //Ширина либо высота имют определенный размер
//        MeasureSpec.AT_MOST   //Ширина либо высота должна быть не более заданного значения (match_parent)
//        MeasureSpec.UNSPECIFIED   //Если данный View имеет wrap_content width/height
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)    //(measureSpec & MODE_MASK)
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)    //(measureSpec & ~MODE_MASK)
        val widthSize = resolveSize(10, widthMeasureSpec)
        val heightSize = resolveSize(10, heightMeasureSpec)
        setMeasuredDimension(widthSize, heightSize)
    }

    /**
     * Вызывается в методе [View.layout]
     * View.layout вызывается после [View.measure]
     *
     * Этот метод позволяет присваивать позицию и размер дочерним элементам ViewGroup.
     * В случае, если мы наследовались от View нам не нужно переопределять этот метод.
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    /**
     * Вызывается в методе [View.draw].
     * View.draw вызывается во время родительского вызова [View.dispatchDraw].
     * View.dispatchDraw вызывается после [View.layout],
     * либо после [View.invalidate]
     *
     * Android обновляет экран не чаще чем раз в 16 мс. Поэтому мы должны уложиться за это время выполнить
     * measure → layout → draw. Также следует учитывать, что onDraw может вызываться много раз.
     * В связи с этим код в onDraw должен быть максимально оптимизированным, чтобы не было лагов.
     * Несколько правил для onDraw:
     * * не создавать внутри него объекты
     * * не делать сложные операции
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}
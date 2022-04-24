package com.github.zharovvv.animationsandbox.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.github.zharovvv.animationsandbox.R

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var progress: Float
    var outBorderColor: Int
    var insideColor: Int
    var borderThickness: Int

    private val paint: Paint

    init {
        val typedArray: TypedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomView)
        outBorderColor =
            typedArray.getColor(
                R.styleable.CustomView_piv_out_border_color,
                resources.getColor(R.color.purple_200, context.theme)
            )
        insideColor = typedArray.getColor(
            R.styleable.CustomView_piv_inside_color,
            resources.getColor(R.color.purple_200, context.theme)
        )
        progress = typedArray.getFloat(R.styleable.CustomView_piv_progress, 0f)
        borderThickness =
            typedArray.getDimensionPixelSize(R.styleable.CustomView_piv_border_thickness, 12)
        typedArray.recycle()
        paint = Paint()
    }

    /**
     * После того как родительский View-компонент вызовет метод addView(View),
     * этот View-компонент будет прикреплён к окну.
     * На этой стадии наш View-компонент будет знать о других View-компонентах,
     * которые его окружают.
     * Если ваш View-компонент работает с View-компонентами пользователя,
     * расположенными в том же самом "layout.xml" файле,
     * то это хорошее место найти их по идентификатору
     * (который вы можете установить с помощью атрибутов)
     * и сохранить их в качестве глобальной ссылки (если нужно).
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    /**
     * Этот метод означает, что наш Custom View-компонент находится
     * на стадии определения собственного размера.
     * Это очень важный метод, так как в большинстве случаев вам нужно определить
     * специфичный размер для вашего View-компонента, чтобы поместиться на вашем макете.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * Этот метод позволяет присваивать размер и позицию дочерним View-компонентам.
     */
    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            canvas.drawARGB(255, 255, 255, 255)
            paint.color = outBorderColor
            canvas.drawRect(0f, 0f, borderThickness.toFloat(), height.toFloat(), paint)
            canvas.drawRect(
                (width - borderThickness).toFloat(),
                0f,
                width.toFloat(),
                height.toFloat(),
                paint
            )
            paint.color = insideColor
            val progressSize = progress / 100f * height
            canvas.drawRect(
                borderThickness.toFloat(),
                0f,
                (width - borderThickness).toFloat(),
                progressSize,
                paint
            )
        }
    }
}
package com.github.zharovvv.core.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDimensionOrThrow
import com.github.zharovvv.core.ui.R

class ClickableCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.clickableCircleViewStyle,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var radius: Float
    private val paint: Paint = Paint()
        .apply { style = Paint.Style.FILL }

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.ClickableCircleView,
            defStyleAttr,
            defStyleRes
        ).use { typedArray ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //for layout inspection tools
                saveAttributeDataForStyleable(
                    context,
                    R.styleable.ClickableCircleView,
                    attrs,
                    typedArray,
                    defStyleAttr,
                    defStyleRes
                )
            }
            paint.color = typedArray.getColorOrThrow(R.styleable.ClickableCircleView_circleColor)
            radius = typedArray.getDimensionOrThrow(R.styleable.ClickableCircleView_circleRadius)
        }
    }


    override fun onDraw(canvas: Canvas) {
//        Log.i(TAG, "ClickableCircleView#onDraw; thread = ${Thread.currentThread()}")
        super.onDraw(canvas)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }

    private companion object {
        const val TAG = "ClickableCircleView"
    }
}
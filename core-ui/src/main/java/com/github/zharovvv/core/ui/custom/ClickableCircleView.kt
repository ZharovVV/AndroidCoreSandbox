package com.github.zharovvv.core.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
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

    private var radius: Float
    private val paint: Paint = Paint()
        .apply { style = Paint.Style.FILL }

    private val gestureDetector: GestureDetector =
        GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                //Обязательно нужно для перехвата onDoubleTap
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                onDoubleTap()
                return true
            }
        })

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
        setOnTouchListener { _, motionEvent -> gestureDetector.onTouchEvent(motionEvent) }
    }

    private val radiusAnimator: ValueAnimator =
        ValueAnimator.ofFloat(radius, radius + 5.dp(), radius)
            .apply {
                addUpdateListener { animator ->
                    radius = animator.animatedValue as Float
                    invalidate()
                }
                duration = 300
            }


    override fun onDraw(canvas: Canvas) {
//        Log.i(TAG, "ClickableCircleView#onDraw; thread = ${Thread.currentThread()}")
        super.onDraw(canvas)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }

    private fun onDoubleTap() {
        if (radiusAnimator.isStarted.not()) {
            radiusAnimator.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        radiusAnimator.cancel()
    }

    private fun Number.dp(): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        resources.displayMetrics
    )


    private companion object {
        const val TAG = "ClickableCircleView"
    }
}
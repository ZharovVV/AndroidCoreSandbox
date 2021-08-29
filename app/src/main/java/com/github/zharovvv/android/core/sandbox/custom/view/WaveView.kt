package com.github.zharovvv.android.core.sandbox.custom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.custom.view.util.interpolateArray

class WaveView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_ITEM_WIDTH_DP = 2
        private const val DEFAULT_ITEM_COLOR = Color.BLACK
        private const val DEFAULT_HEIGHT = 50
    }

    private val wavePath = Path()
    private val linePaint = Paint()

    private var itemWidth: Int = 0
    private var defaultHeightPixelSize: Int = 0
    private val originalData: IntArray = getWaveData()

    private var measuredData: IntArray? = null

    init {
        val displayMetrics = context.resources.displayMetrics
        var itemWidthFromAttr = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DEFAULT_ITEM_WIDTH_DP.toFloat(),
            displayMetrics
        ).toInt()
        defaultHeightPixelSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DEFAULT_HEIGHT.toFloat(),
            displayMetrics
        ).toInt()
        var itemColorFromAttr = DEFAULT_ITEM_COLOR
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView)
            itemWidthFromAttr =
                typedArray.getDimensionPixelSize(R.styleable.WaveView_itemWidth, itemWidthFromAttr)
            itemColorFromAttr =
                typedArray.getColor(R.styleable.WaveView_itemColor, itemColorFromAttr)
            typedArray.recycle()
        }
        itemWidth = itemWidthFromAttr
        linePaint.style = Paint.Style.STROKE
        linePaint.color = itemColorFromAttr
        linePaint.strokeWidth = itemWidthFromAttr.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        val leftPadding = paddingLeft
        val rightPadding = paddingRight
        var width = originalData.size * itemWidth * 2 - itemWidth + leftPadding + rightPadding
        width = resolveSize(width, widthMeasureSpec)
        var height = if (measuredHeight > defaultHeightPixelSize) {
            defaultHeightPixelSize
        } else {
            measuredHeight
        }
        height = resolveSize(height, heightMeasureSpec)
        val itemCount = (measuredWidth + itemWidth - leftPadding - rightPadding) / (itemWidth * 2)
        measuredData = interpolateArray(originalData, itemCount)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        if (measuredData == null) {
            return
        }
        wavePath.reset()
        val measuredHeight: Int = measuredHeight - paddingTop - paddingBottom
        var currentX: Float = itemWidth.toFloat() + paddingLeft
        for (data in measuredData!!) {
            val itemHeight: Float = (data.toFloat() / MAX_VOLUME) * measuredHeight
            val startY: Float = (measuredHeight.toFloat() - itemHeight) / 2 + paddingTop
            val endY: Float = startY + itemHeight
            wavePath.moveTo(currentX, startY)
            wavePath.lineTo(currentX, endY)
            currentX += itemWidth * 2
        }
        canvas?.drawPath(wavePath, linePaint)
    }

}
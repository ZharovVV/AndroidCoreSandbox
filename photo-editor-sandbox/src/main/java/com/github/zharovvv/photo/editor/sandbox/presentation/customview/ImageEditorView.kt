package com.github.zharovvv.photo.editor.sandbox.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.github.zharovvv.core.ui.gestures.debugInfo
import com.github.zharovvv.photo.editor.sandbox.R
import java.lang.Float.min
import kotlin.math.round

class ImageEditorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), OnTouchListener {

    private val path = Path()
    private val paint: Paint = Paint().apply {
        pathEffect = CornerPathEffect(10f)
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 50f
        color = resources.getColor(R.color.ds_purple_200, context.theme)
    }
    private val matrix: Matrix = Matrix()
    private val invertMatrix = Matrix()
    private var _sourceImageBitmapDrawable: BitmapDrawable? = null
    private val defaultDrawable: Drawable = ColorDrawable(
        TypedValue().also {
            context.theme.resolveAttribute(R.attr.colorBackgroundFloating, it, true)
        }.data
    )

    init {
        setOnTouchListener(this)
    }

    fun setImageUri(uri: Uri) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        //TODO Worker Thread
        val bitmap = ImageDecoder.decodeBitmap(source)
        val sourceBitmapDrawable = _sourceImageBitmapDrawable
        if (sourceBitmapDrawable == null) {
            _sourceImageBitmapDrawable = BitmapDrawable(resources, bitmap)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                sourceBitmapDrawable.bitmap = bitmap
            } else {
                _sourceImageBitmapDrawable = BitmapDrawable(resources, bitmap)
            }
        }
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val drawable = _sourceImageBitmapDrawable
        if (drawable != null) {
            val vWidth = width
            val vHeight = height
            val dWidth = drawable.intrinsicWidth
            val dHeight = drawable.intrinsicHeight
            drawable.setBounds(0, 0, dWidth, dHeight)
            val scale = if (dWidth <= vWidth && dHeight <= vHeight) {
                1.0f
            } else {
                min(vWidth.toFloat() / dWidth.toFloat(), vHeight.toFloat() / dHeight.toFloat())
            }
            val dx = round((vWidth - dWidth * scale) * 0.5f)
            val dy = round((vHeight - dHeight * scale) * 0.5f)
            matrix.setScale(scale, scale)
            matrix.postTranslate(dx, dy)
            invertMatrix.set(matrix)
            invertMatrix.invert(invertMatrix)
            paint.strokeWidth = invertMatrix.mapRadius(50f)
        }
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = _sourceImageBitmapDrawable ?: defaultDrawable
        canvas.concat(matrix)
        drawable.draw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        Log.i(
            "PhotoEditor",
            "onTouch; ${event.debugInfo}"
        )
        event.transform(invertMatrix)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                path.moveTo(event.x, event.y)
            }

            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                path.lineTo(event.x, event.y)
                invalidate()
            }
        }
        return true
    }
}
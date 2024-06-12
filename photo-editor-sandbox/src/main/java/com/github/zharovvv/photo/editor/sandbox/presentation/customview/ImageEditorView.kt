package com.github.zharovvv.photo.editor.sandbox.presentation.customview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.View.OnTouchListener
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

    private val initialDrawableBounds: RectF = RectF(0f, 0f, 0f, 0f)
    private val drawableBounds: RectF = RectF(0f, 0f, 0f, 0f)

    private var sourceDrawableWidth: Float = 0f
    private var sourceDrawableHeight: Float = 0f
    private var minScale = 1f
    private var maxScale = 2f
    private var scale = 1f

    private val lastFocus: PointF = PointF()
    private var multiTouch: Boolean = false


    //TODO Вынести в свойство кастомного аниматора
    private var targetScale: Float = 0f
    private val scaleAnimator: ValueAnimator = ValueAnimator()
        .apply {
            duration = 200
            val targetMatrix = Matrix()
            val targetDrawableBounds = RectF(0f, 0f, 0f, 0f)
            var targetDx: Float = 0f
            var targetDy: Float = 0f
            var currentDx = 0f
            var currentDy = 0f
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    targetMatrix.set(matrix)
                    val scaleFactor = targetScale / scale
                    targetMatrix.postScale(scaleFactor, scaleFactor, lastFocus.x, lastFocus.y)
                    targetDrawableBounds.set(0f, 0f, sourceDrawableWidth, sourceDrawableHeight)
                    targetMatrix.mapRect(targetDrawableBounds)
                    if (targetScale == minScale) {
                        targetDx = initialDrawableBounds.left - targetDrawableBounds.left
                        targetDy = initialDrawableBounds.top - targetDrawableBounds.top
                    } else {
                        targetDx = when {
                            targetDrawableBounds.width() < width -> (width - targetDrawableBounds.width()) / 2 - targetDrawableBounds.left
                            targetDrawableBounds.right < width -> width - targetDrawableBounds.right
                            targetDrawableBounds.left > 0 -> -targetDrawableBounds.left
                            else -> 0f
                        }
                        targetDy = when {
                            targetDrawableBounds.height() < height -> (height - targetDrawableBounds.height()) / 2 - targetDrawableBounds.top
                            targetDrawableBounds.bottom < height -> height - targetDrawableBounds.bottom
                            targetDrawableBounds.top > 0 -> -targetDrawableBounds.top
                            else -> 0f
                        }
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    targetMatrix.reset()
                    currentDx = 0f
                    currentDy = 0f
//                    Log.i("ImageEditorView", "onAnimationEnd")
                }

                override fun onAnimationCancel(animation: Animator) {
                    targetMatrix.reset()
                    currentDx = 0f
                    currentDy = 0f
//                    Log.i("ImageEditorView", "onAnimationCancel")
                }
            })
            addUpdateListener { animator ->
                val dx = targetDx * animatedFraction - currentDx
                val dy = targetDy * animatedFraction - currentDy
                val nextFrameScale = animator.animatedValue as Float
                val scaleFactor = nextFrameScale / scale
                matrix.postScale(scaleFactor, scaleFactor, lastFocus.x, lastFocus.y)
                scale *= scaleFactor
                matrix.postTranslate(dx, dy)
                lastFocus.set(lastFocus.x + dx, lastFocus.y + dy)
                Log.i(
                    "ImageEditorView", "onAnimationUpdate\n" +
                            "currentDx = $currentDx; currentDy = $currentDy; dx = $dx; dy = $dy"
                )
                currentDx += dx
                currentDy += dy

                drawableBounds.set(0f, 0f, sourceDrawableWidth, sourceDrawableHeight)
                matrix.mapRect(drawableBounds)
                invalidate()
            }
        }

    private val scaleGestureDetector =
        ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scale *= detector.scaleFactor
//                scaleFactor = min(max(scaleFactor, 1f), 4f)
//                Log.i(
//                    "ImageEditorView",
//                    "onScale; scaleFactor = $scale;fX = ${detector.focusX};fY = ${detector.focusY}"
//                )

//                matrix.setScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
                matrix.postScale(
                    detector.scaleFactor,
                    detector.scaleFactor,
                    detector.focusX,
                    detector.focusY
                )
                lastFocus.set(detector.focusX, detector.focusY)
                drawableBounds.set(0f, 0f, sourceDrawableWidth, sourceDrawableHeight)
                matrix.mapRect(drawableBounds)
//                matrix.postTranslate()

//                invertMatrix.set(matrix)
//
//                invertMatrix.invert(invertMatrix)
//                paint.strokeWidth = invertMatrix.mapRadius(50f)
                invalidate()
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
                targetScale = when {
                    scale < minScale -> minScale
                    scale > maxScale -> maxScale
                    else -> scale
                }
                scaleAnimator.setFloatValues(scale, targetScale)
                scaleAnimator.start()
            }
        })

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
            sourceDrawableWidth = dWidth.toFloat()
            sourceDrawableHeight = dHeight.toFloat()
            drawable.setBounds(0, 0, dWidth, dHeight)
            val scale = if (dWidth <= vWidth && dHeight <= vHeight) {
                1.0f
            } else {
                min(vWidth.toFloat() / dWidth.toFloat(), vHeight.toFloat() / dHeight.toFloat())
            }
            val dx = round((vWidth - dWidth * scale) * 0.5f)
            val dy = round((vHeight - dHeight * scale) * 0.5f)
            this.scale = scale
            minScale = scale
            maxScale = 3 * scale
            matrix.setScale(scale, scale)
            matrix.postTranslate(dx, dy)

            drawableBounds.set(0f, 0f, dWidth.toFloat(), dHeight.toFloat())
            matrix.mapRect(drawableBounds)
            initialDrawableBounds.set(drawableBounds)
//            invertMatrix.set(matrix)
//            invertMatrix.invert(invertMatrix)
//            paint.strokeWidth = invertMatrix.mapRadius(50f)
        }
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = _sourceImageBitmapDrawable ?: defaultDrawable
        canvas.concat(matrix)
        drawable.draw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (scaleAnimator.isRunning) return true
//        Log.i(
//            "PhotoEditor",
//            "onTouch; ${event.debugInfo}"
//        )
        scaleGestureDetector.onTouchEvent(event)
//        event.transform(invertMatrix)
//        when (event.actionMasked) {
//            MotionEvent.ACTION_DOWN -> {
//                path.reset()
//                path.moveTo(event.x, event.y)
//            }
//
//            MotionEvent.ACTION_POINTER_DOWN -> {
//                multiTouch = true
//            }
//
//            MotionEvent.ACTION_UP -> {
//                multiTouch = false
//            }
//        }
//        if (multiTouch.not()) {
//            when (event.actionMasked) {
//                MotionEvent.ACTION_MOVE -> {
//                    path.lineTo(event.x, event.y)
//                    invalidate()
//                }
//
//                MotionEvent.ACTION_UP -> {
//                    path.lineTo(event.x, event.y)
//                    invalidate()
//                }
//            }
//        }
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scaleAnimator.cancel()
    }
}
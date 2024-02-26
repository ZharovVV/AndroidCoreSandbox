package com.github.zharovvv.android.core.sandbox.multitouch

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import com.github.zharovvv.android.core.sandbox.databinding.ActivityMultitouchExampleBinding
import com.github.zharovvv.android.core.sandbox.multitouch.DoubleTouchTranslateGestureDetector.OnDoubleTouchTranslateListener
import com.github.zharovvv.core.ui.gestures.debugInfo

internal class MultiTouchActivity : AppCompatActivity() {

    private val scaleAnimator: ValueAnimator = ValueAnimator().apply { duration = 200 }
    private val translateXAnimator: ValueAnimator = ValueAnimator().apply { duration = 200 }
    private val translateYAnimator: ValueAnimator = ValueAnimator().apply { duration = 200 }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMultitouchExampleBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        var scaleFactor = 1f
        var simpleViewDefaultCoords: PointF? = null
        binding.simpleView.doOnLayout {
            simpleViewDefaultCoords = PointF(it.x, it.y)
        }
        scaleAnimator.addUpdateListener { animator ->
            val animatedScale = animator.animatedValue as Float
            scaleFactor = animatedScale
            binding.simpleView.scaleX = animatedScale
            binding.simpleView.scaleY = animatedScale
        }
        translateXAnimator.addUpdateListener { animator ->
            val animatedX = animator.animatedValue as Float
            binding.simpleView.x = animatedX
        }
        translateYAnimator.addUpdateListener { animator ->
            val animatedY = animator.animatedValue as Float
            binding.simpleView.y = animatedY
        }
        val scaleGestureDetector = ScaleGestureDetector(this, object : OnScaleGestureListener {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
//                Log.i(TAG, "onScale; scaleFactor = $scaleFactor")
                binding.simpleView.scaleX = scaleFactor
                binding.simpleView.scaleY = scaleFactor
                return true
            }

            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
//                Log.i(TAG, "onScaleBegin")
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
                if (scaleFactor > MAX_STATIC_SCALE_FACTOR) {
                    scaleAnimator.setFloatValues(scaleFactor, MAX_STATIC_SCALE_FACTOR)
                    scaleAnimator.start()
                }
                if (scaleFactor < MIN_STATIC_SCALE_FACTOR) {
                    scaleAnimator.setFloatValues(scaleFactor, MIN_STATIC_SCALE_FACTOR)
                    scaleAnimator.start()
                }
            }
        })

        val doubleTouchTranslateGestureDetector = DoubleTouchTranslateGestureDetector(
            object : OnDoubleTouchTranslateListener {
                override fun onDoubleTouchTranslate(dx: Float, dy: Float) {
                    val oldX = binding.simpleView.x
                    val oldY = binding.simpleView.y
                    binding.simpleView.x = oldX + dx
                    binding.simpleView.y = oldY + dy
                }

                override fun onDoubleTouchTranslateEnd() {
                    val defaultCoords = simpleViewDefaultCoords
                    if (scaleFactor <= MIN_STATIC_SCALE_FACTOR && defaultCoords != null) {
                        translateXAnimator.setFloatValues(binding.simpleView.x, defaultCoords.x)
                        translateYAnimator.setFloatValues(binding.simpleView.y, defaultCoords.y)
                        translateXAnimator.start()
                        translateYAnimator.start()
                    }
                }
            }
        )

        binding.root.setOnTouchListener { _, motionEvent ->
            Log.i(TAG, "onTouch; ${motionEvent.debugInfo}")
            val scaleConsumed = scaleGestureDetector.onTouchEvent(motionEvent)
            val doubleTouchTranslateConsumed =
                doubleTouchTranslateGestureDetector.onTouchEvent(motionEvent)
            scaleConsumed || doubleTouchTranslateConsumed
        }
    }

    override fun onStop() {
        super.onStop()
        scaleAnimator.cancel()
        translateXAnimator.cancel()
        translateYAnimator.cancel()
    }

    private companion object {
        const val TAG = "MultiTouchTag"
        const val MIN_STATIC_SCALE_FACTOR = 1f
        const val MAX_STATIC_SCALE_FACTOR = 4f
    }
}
package com.github.zharovvv.animationsandbox.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.github.zharovvv.animationsandbox.R
import com.github.zharovvv.animationsandbox.views.CustomView

class PropertyAnimationExampleFragment : Fragment() {

    private lateinit var rotatedImageView: ImageView

    private lateinit var rotateValueAnimator: ValueAnimator

    private lateinit var testImageView: ImageView

    private lateinit var customView: CustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_property_animation_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRotateAnimator(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpRotateAnimator(view: View) {
        rotatedImageView = view.findViewById(R.id.image_view_property_animation)
        rotateValueAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                rotatedImageView.rotation = value
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationRepeat(animation: Animator?) {
                    super.onAnimationRepeat(animation)
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                }

                override fun onAnimationCancel(animation: Animator?) {
                    super.onAnimationCancel(animation)
                }

                override fun onAnimationPause(animation: Animator?) {
                    super.onAnimationPause(animation)
                }

                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                }

                override fun onAnimationResume(animation: Animator?) {
                    super.onAnimationResume(animation)
                }
            })
            duration = 3000
            setInterpolator { progress ->
                progress
            }
        }

        testImageView = view.findViewById(R.id.test_image_vew)
        var touchX = 0.0f
        var touchY = 0.0f
        var lastTouchId: Int? = null
        testImageView.setOnTouchListener { v, event ->
            when (event.action) {
                ACTION_DOWN -> {
                    v.setBackgroundResource(R.color.purple_700)
                    touchX = event.rawX
                    touchY = event.rawY
                }
                ACTION_MOVE -> {
                    val dx = event.rawX - touchX
                    touchX += dx
                    val dy = event.rawY - touchY
                    touchY += dy
                    v.animate()
                        .x(v.x + dx)
                        .y(v.y + dy)
                        .setDuration(0L)
                        .start()
                }
                else -> {
                    touchX = event.x
                    touchY = event.y
                    v.setBackgroundResource(R.color.purple_200)
                }
            }
            true
        }

        rotatedImageView.setOnClickListener {
            if (rotateValueAnimator.isStarted) {
                rotateValueAnimator.end()
            } else {
                rotateValueAnimator.start()
            }
        }

        customView = view.findViewById(R.id.custom_view)
        val customViewAnimator = ValueAnimator.ofFloat(0f, 100f)
            .apply {
                duration = 1500
                addUpdateListener {
                    customView.progress = it.animatedValue as Float
                    customView.invalidate()
                }
            }
        customView.setOnClickListener {
            customViewAnimator.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        rotateValueAnimator.end()
    }
}
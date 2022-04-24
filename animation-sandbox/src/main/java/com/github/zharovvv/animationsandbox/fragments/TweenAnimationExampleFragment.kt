package com.github.zharovvv.animationsandbox.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.zharovvv.animationsandbox.R
import java.text.DecimalFormat
import kotlin.math.pow

class TweenAnimationExampleFragment : Fragment() {

    private lateinit var alphaImageView: ImageView
    private lateinit var alphaFadeInAnimation: Animation
    private lateinit var alphaFadeOutAnimation: Animation

    private lateinit var scaleImageView: ImageView
    private lateinit var scaleProgress: TextView
    private lateinit var scaleInterpolation: TextView

    private lateinit var translateImageView: ImageView

    private lateinit var rotateImageView: ImageView

    private lateinit var combinationImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tween_animation_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAlphaAnimation(view)
        setUpScaleAnimation(view)
        setUpTranslateAnimation(view)
        setUpRotateAnimation(view)
        setUpCombinationAnimation(view)
    }

    private fun setUpAlphaAnimation(rootView: View) {
        alphaImageView = rootView.findViewById(R.id.image_view_alpha)
        alphaFadeInAnimation = AnimationUtils.loadAnimation(rootView.context, R.anim.alpha_fadein)
        alphaFadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                alphaImageView.startAnimation(alphaFadeOutAnimation)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        alphaFadeOutAnimation = AnimationUtils.loadAnimation(rootView.context, R.anim.alpha_fadeout)
        alphaFadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        alphaImageView.setOnClickListener {
            it.startAnimation(alphaFadeInAnimation)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpScaleAnimation(rootView: View) {
        scaleImageView = rootView.findViewById(R.id.image_view_scale)
        scaleProgress = rootView.findViewById(R.id.progress)
        scaleInterpolation = rootView.findViewById(R.id.interpolation)
        val scaleAnimation = AnimationUtils.loadAnimation(rootView.context, R.anim.scale)
        val decimalFormat = DecimalFormat("#.##")
        scaleAnimation.setInterpolator { progress ->
            scaleProgress.text = "Progress : ${decimalFormat.format(progress.toDouble())}"

            val interpolation = progress.toDouble().pow(2.0).toFloat()

            scaleInterpolation.text =
                "Interpolation: ${decimalFormat.format(interpolation.toDouble())}"
            interpolation
        }
        scaleImageView.setOnClickListener {
            it.startAnimation(scaleAnimation)
        }
    }

    private fun setUpTranslateAnimation(rootView: View) {
        translateImageView = rootView.findViewById(R.id.image_view_translate)
        val translateAnimation = AnimationUtils.loadAnimation(rootView.context, R.anim.translate)
        translateAnimation.setInterpolator { progress ->
            (1.0f - (1.0f - progress) * (1.0f - progress))
        }
        translateImageView.setOnClickListener {
            it.startAnimation(translateAnimation)
        }
    }

    private fun setUpRotateAnimation(rootView: View) {
        rotateImageView = rootView.findViewById(R.id.image_view_rotate)
        rotateImageView.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(rootView.context, R.anim.rotate))
        }
    }

    private fun setUpCombinationAnimation(rootView: View) {
        combinationImageView = rootView.findViewById(R.id.image_view_combination)
        combinationImageView.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(rootView.context, R.anim.combination))
        }
    }
}
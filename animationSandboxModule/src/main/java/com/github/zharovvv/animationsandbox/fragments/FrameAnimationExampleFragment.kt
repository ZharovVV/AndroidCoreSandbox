package com.github.zharovvv.animationsandbox.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.github.zharovvv.animationsandbox.R

class FrameAnimationExampleFragment : Fragment() {

    private lateinit var frameImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_frame_animation_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        frameImageView = view.findViewById(R.id.image_view_frame)
        frameImageView.setBackgroundResource(R.drawable.frame_animation)
        val animationDrawable = frameImageView.background as AnimationDrawable
        frameImageView.setOnClickListener {
            if (animationDrawable.isRunning) {
                animationDrawable.stop()
            }
            animationDrawable.start()
        }
    }
}
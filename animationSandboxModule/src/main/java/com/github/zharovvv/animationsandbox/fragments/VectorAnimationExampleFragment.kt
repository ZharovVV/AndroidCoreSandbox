package com.github.zharovvv.animationsandbox.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.github.zharovvv.animationsandbox.R

class VectorAnimationExampleFragment : Fragment() {

    private lateinit var drawerImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vector_animation_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        drawerImageView = view.findViewById(R.id.drawer)
        var isChecked = false
        drawerImageView.setOnClickListener {
            isChecked = !isChecked
            drawerImageView.setImageState(
                IntArray(
                    1
                ) {
                    if (isChecked) {
                        android.R.attr.state_checked
                    } else {
                        -100500
                    }
                }, true
            )
        }
    }
}
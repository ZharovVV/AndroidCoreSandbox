package com.github.zharovvv.graphics.presentation.fragments

import android.app.ActivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.github.zharovvv.graphics.databinding.FragmentOpenGlBinding
import com.github.zharovvv.graphics.opengl.OpenGLRenderer

class OpenGLFragment : Fragment() {

    private var _binding: FragmentOpenGlBinding? = null
    private val binding: FragmentOpenGlBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenGlBinding.inflate(inflater, container, false)
        with(binding.glSurfaceView) {
            setEGLContextClientVersion(2)
            setRenderer(OpenGLRenderer())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (supportES2().not()) {
            Toast.makeText(requireActivity(), "OpenGl ES 2.0 is not supported", Toast.LENGTH_LONG)
                .show()
        }
        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    ON_RESUME -> binding.glSurfaceView.onResume()
                    ON_PAUSE -> binding.glSurfaceView.onPause()
                    ON_DESTROY -> source.lifecycle.removeObserver(this)
                    else -> {}
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun supportES2(): Boolean {
        val activityManager = getSystemService(requireContext(), ActivityManager::class.java)
            ?: return false
        val configurationInfo = activityManager.deviceConfigurationInfo
        return configurationInfo.reqGlEsVersion >= 0x20000
    }

    companion object {
        const val TAG = "OpenGLFragment"
    }
}
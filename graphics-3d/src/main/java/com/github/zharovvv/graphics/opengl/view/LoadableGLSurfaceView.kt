package com.github.zharovvv.graphics.opengl.view

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.github.zharovvv.graphics.R
import com.github.zharovvv.graphics.opengl.BaseRenderer

class LoadableGLSurfaceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val processBar: ProgressBar
    private val glSurfaceView: GLSurfaceView
    private var glIsReady: Boolean = false
    private var isResumed: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.loadable_gl_surface_view, this)
        processBar = findViewById(R.id.progress_bar)
        glSurfaceView = findViewById(R.id.gl_surface_view)
    }

    private val failRenderListener: () -> Unit = {
        glIsReady = false
        glSurfaceView.isVisible = false
        processBar.isVisible = true
    }

    fun onRendererReady(renderer: BaseRenderer) {
        with(glSurfaceView) {
            setEGLContextClientVersion(2)
            setRenderer(renderer)
            renderer.addOnFailRenderListener(failRenderListener)
            isVisible = true
            if (isResumed) {
                onResume()
            }
            processBar.isVisible = false
        }
        glIsReady = true
    }

    fun associateWith(lifecycle: Lifecycle) {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        if (glIsReady) glSurfaceView.onResume()
                        isResumed = true
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        if (glIsReady) glSurfaceView.onPause()
                        isResumed = false
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        source.lifecycle.removeObserver(this)
                    }
                    else -> {}
                }
            }
        })
    }
}
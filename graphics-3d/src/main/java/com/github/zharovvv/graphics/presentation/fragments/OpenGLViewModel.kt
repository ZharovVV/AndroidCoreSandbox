package com.github.zharovvv.graphics.presentation.fragments

import android.opengl.GLES20.GL_FRAGMENT_SHADER
import android.opengl.GLES20.GL_VERTEX_SHADER
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zharovvv.graphics.R
import com.github.zharovvv.graphics.opengl.shader.ShaderSourceLoader
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

internal class OpenGLViewModel(
    private val shaderSourceLoader: ShaderSourceLoader
) : ViewModel() {

    private val _shaderSource: MutableStateFlow<List<ShaderSource>?> = MutableStateFlow(null)
    val shaderSource: Flow<List<ShaderSource>> = _shaderSource.filterNotNull()

    init {
        viewModelScope.launch {
            val vertexShaderSource = async {
                shaderSourceLoader.loadFromRaw(R.raw.vertex_shader, GL_VERTEX_SHADER)
            }
            val fragmentShaderSource = async {
                shaderSourceLoader.loadFromRaw(R.raw.fragment_shader, GL_FRAGMENT_SHADER)
            }
            _shaderSource.value = listOf(
                vertexShaderSource.await(),
                fragmentShaderSource.await()
            )
        }
    }
}
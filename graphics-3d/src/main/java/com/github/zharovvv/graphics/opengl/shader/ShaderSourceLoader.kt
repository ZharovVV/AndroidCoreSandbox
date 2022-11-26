package com.github.zharovvv.graphics.opengl.shader

import androidx.annotation.RawRes
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource

/**
 * Загрузчик шейдеров.
 */
interface ShaderSourceLoader {

    suspend fun loadFromRaw(@RawRes shaderResId: Int, type: Int): ShaderSource

    suspend fun loadFromUrl(shaderUrl: String, type: Int): ShaderSource
}
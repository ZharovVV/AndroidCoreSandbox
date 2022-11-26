package com.github.zharovvv.graphics.opengl.shader

import androidx.annotation.RawRes

/**
 * Reader шейдеров.
 */
interface ShaderSourceReader {

    fun readFromRaw(@RawRes shaderResId: Int): String

    fun readFromUrl(shaderUrl: String): String
}
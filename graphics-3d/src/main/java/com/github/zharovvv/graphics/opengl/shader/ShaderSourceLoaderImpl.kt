package com.github.zharovvv.graphics.opengl.shader

import android.content.Context
import androidx.annotation.RawRes
import com.github.zharovvv.graphics.opengl.shader.model.ShaderSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

internal class ShaderSourceLoaderImpl(private val context: Context) : ShaderSourceLoader {

    override suspend fun loadFromRaw(@RawRes shaderResId: Int, type: Int): ShaderSource =
        withContext(Dispatchers.IO) {
            ShaderSource(
                type = type,
                source = InputStreamReader(context.resources.openRawResource(shaderResId))
                    .useLines { stringSequence ->
                        stringSequence.joinToString(separator = "\r\n") { it }
                    }
            )
        }

    override suspend fun loadFromUrl(shaderUrl: String, type: Int): ShaderSource {
        TODO("Not yet implemented")
    }
}
package com.github.zharovvv.graphics.opengl.shader

import android.content.Context
import androidx.annotation.RawRes
import java.io.InputStreamReader

internal class ShaderSourceReaderImpl(private val context: Context) : ShaderSourceReader {

    override fun readFromRaw(@RawRes shaderResId: Int): String =
        InputStreamReader(context.resources.openRawResource(shaderResId))
            .useLines { stringSequence -> stringSequence.joinToString { line: String -> line } }

    override fun readFromUrl(shaderUrl: String): String {
        TODO("Not yet implemented")
    }
}
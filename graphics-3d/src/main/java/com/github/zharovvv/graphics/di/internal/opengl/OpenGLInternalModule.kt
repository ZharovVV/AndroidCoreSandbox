package com.github.zharovvv.graphics.di.internal.opengl

import android.content.Context
import com.github.zharovvv.common.di.qualifier.ApplicationContext
import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.graphics.opengl.OpenGLEngine
import com.github.zharovvv.graphics.opengl.OpenGLEngineImpl
import com.github.zharovvv.graphics.opengl.program.ProgramCreatorImpl
import com.github.zharovvv.graphics.opengl.shader.ShaderCreatorImpl
import com.github.zharovvv.graphics.opengl.shader.ShaderSourceLoader
import com.github.zharovvv.graphics.opengl.shader.ShaderSourceLoaderImpl
import dagger.Module
import dagger.Provides

@Module
class OpenGLInternalModule {

    @PerFeature
    @Provides
    fun shaderSourceLoader(
        @ApplicationContext context: Context
    ): ShaderSourceLoader = ShaderSourceLoaderImpl(context)

    @PerFeature
    @Provides
    fun openGLEngine(): OpenGLEngine = OpenGLEngineImpl(
        programCreator = ProgramCreatorImpl(),
        shaderCreator = ShaderCreatorImpl()
    )
}
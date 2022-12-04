package com.github.zharovvv.graphics.presentation.fragments

import android.app.ActivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.zharovvv.common.di.internalFeatureApi
import com.github.zharovvv.graphics.databinding.FragmentOpenGlBinding
import com.github.zharovvv.graphics.di.api.Graphics3DApi
import com.github.zharovvv.graphics.di.internal.Graphics3DInternalApi
import com.github.zharovvv.graphics.di.internal.ui.diViewModels
import com.github.zharovvv.graphics.opengl.PerspectiveRenderer
import com.github.zharovvv.graphics.opengl.PrimitivesRenderer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OpenGLFragment : Fragment() {

    private val openGLViewModel: OpenGLViewModel by diViewModels()
    private var _binding: FragmentOpenGlBinding? = null
    private val binding: FragmentOpenGlBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenGlBinding.inflate(inflater, container, false)
        initShader()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (supportES2().not()) {
            Toast.makeText(requireActivity(), "OpenGl ES 2.0 is not supported", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * **Шейдеры** – это программы, написанные на языке GLSL.
     * В 3D графике все изображение строится из графических примитивов:
     * точек, линий, треугольников.
     *
     * Чтобы нарисовать примитив, графический процессор должен знать координаты его вершин
     * и цвет заливки для каждой точки. Именно эту информацию и предоставляют ему шейдеры.
     * И, соответственно, существует два типа шейдеров:
     * * *вершинные*, которые оперируют вершинами графических примитивов.
     * * *фрагментные*, отвечают за цвет каждой точки графических примитивов
     * * (есть ещё) геометрический *шейдер*.
     *
     * Т.е. если мы рисуем, например, треугольник, то окончательные координаты его вершин
     * будут определены в вершинном шейдере. Этот шейдер будет вызван один раз для каждой вершины.
     *
     * А цвет каждой точки треугольника будет определен в фрагментном шейдере.
     * Этот шейдер будет вызван для каждой точки треугольника.
     *
     * От нас требуется создать эти шейдеры и передать в них данные из нашего приложения.
     */
    private fun initShader() {
        openGLViewModel.primitiveShaderSource
            .onEach { shaderSources ->
                val internalApi: Graphics3DInternalApi =
                    internalFeatureApi<Graphics3DApi, Graphics3DInternalApi>()
                val renderer = PrimitivesRenderer(internalApi.openGLEngine, shaderSources)
                with(binding.shaderGlSurfaceView) {
                    associateWith(viewLifecycleOwner.lifecycle)
                    onRendererReady(renderer)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        openGLViewModel.perspectiveShaderSource
            .onEach { shaderSources ->
                val internalApi: Graphics3DInternalApi =
                    internalFeatureApi<Graphics3DApi, Graphics3DInternalApi>()
                val renderer = PerspectiveRenderer(internalApi.openGLEngine, shaderSources)
                with(binding.perspectiveGlSurfaceView) {
                    associateWith(viewLifecycleOwner.lifecycle)
                    onRendererReady(renderer)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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
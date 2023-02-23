package com.github.zharovvv.graphics.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.zharovvv.graphics.databinding.FragmentSceneViewBinding
import com.google.android.filament.IndirectLight
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Light
import com.google.ar.sceneform.rendering.ModelRenderable
import com.gorisse.thomas.sceneform.Filament.engine
import com.gorisse.thomas.sceneform.environment
import com.gorisse.thomas.sceneform.environment.Environment
import kotlinx.coroutines.future.asDeferred
import kotlinx.coroutines.launch

class SceneViewFragment : Fragment() {

    private var _binding: FragmentSceneViewBinding? = null
    private val binding: FragmentSceneViewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSceneViewBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sceneView.apply {
            scene.addOnPeekTouchListener { hitTestResult, motionEvent ->
                when (motionEvent.action) {
                    ACTION_MOVE -> {

                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                val deferredModel = ModelRenderable.builder()
                    .setSource(requireContext(), Uri.parse("coin.glb"))
                    .setIsFilamentGltf(true)
//                    .setAsyncLoadEnabled(true)
                    .build().asDeferred()
                val modelNode = Node().apply {
                    renderable = deferredModel.await()
                    localPosition = Vector3(0f, 0f, -2f)
                    localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 35f)
                }
                val lightNode = Node().apply {
                    localPosition = Vector3(0f, 0f, 2f)
                    light = Light.builder(Light.Type.DIRECTIONAL)
                        .setShadowCastingEnabled(true)
                        .setColorTemperature(50000f)
                        .setIntensity(110000f)
                        .build()
                }
                environment = Environment(
                    indirectLight = IndirectLight.Builder()
                        .intensity(20000f)
                        .radiance(1, floatArrayOf(1f, 1f, 1f))
                        .irradiance(1, floatArrayOf(1f, 1f, 1f))
                        .build(engine)
                )
//                environment = HDRLoader.loadEnvironment(
//                    context = requireContext(),
//                    hdrFileLocation = "studio_small_09_2k.hdr"
//                )
                scene.addChild(lightNode)
                scene.addChild(modelNode)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.sceneView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.sceneView.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SceneViewFragment"
    }
}
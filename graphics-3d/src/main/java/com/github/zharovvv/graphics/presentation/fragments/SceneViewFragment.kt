package com.github.zharovvv.graphics.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.zharovvv.graphics.databinding.FragmentSceneViewBinding
import io.github.sceneview.loaders.loadHdrIndirectLight
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.nodes.CameraNode
import io.github.sceneview.nodes.ModelNode

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
        binding.sceneView.setLifecycle(viewLifecycleOwner.lifecycle)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            with(binding.sceneView) {
                loadHdrIndirectLight("studio_small_09_2k.hdr", specularFilter = true) {
                    intensity(30_000f)
                }
//                loadHdrSkybox("studio_small_09_2k.hdr") {
//                    intensity(50_000f)
//                }
                val model = modelLoader.loadModel("axe.glb")!!
                val modelNode = ModelNode(this, model).apply {
                    transform(
                        position = Position(z = -4.0f),
                        rotation = Rotation(x = 0.0f)
                    )
                    scaleToUnitsCube(2.0f)
                    // TODO: Fix centerOrigin
//                centerOrigin(Position(x=-1.0f, y=-1.0f))
                }
                addChildNode(CameraNode(this) {
                })
                addChildNode(modelNode)
                //Задание света вручную работает как-то криво((
//                addChildNode(LightNode(this, LightManager.Type.POINT) {
//                    intensity(100_000f)
//                    this.position(0f, 0f, 10f)
////                    this.sunHaloSize(50f)
//                    direction(-1f, 0f, -3f)
//                })
//                addChildNode(LightNode(this, LightManager.Type.POINT) {
//                    intensity(100_000f)
//                    this.position(0f, 0f, -10f)
////                    this.sunHaloSize(50f)
//                    direction(-1f, 0f, -3f)
//                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SceneViewFragment"
    }
}
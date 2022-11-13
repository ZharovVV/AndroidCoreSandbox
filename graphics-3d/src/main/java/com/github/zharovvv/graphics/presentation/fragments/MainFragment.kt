package com.github.zharovvv.graphics.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.zharovvv.common.di.internalFeatureApi
import com.github.zharovvv.core.navigation.EntryPoint.FragmentEntryPoint
import com.github.zharovvv.core.navigation.ui.DefaultEntryPointsAdapter
import com.github.zharovvv.graphics.R
import com.github.zharovvv.graphics.databinding.FragmentMainGraphics3dBinding
import com.github.zharovvv.graphics.di.api.Graphics3DApi
import com.github.zharovvv.graphics.di.internal.Graphics3DInternalApi

class MainFragment : Fragment() {

    private var _binding: FragmentMainGraphics3dBinding? = null
    private val binding: FragmentMainGraphics3dBinding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainGraphics3dBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = DefaultEntryPointsAdapter<FragmentEntryPoint>(
            onItemClick = { entryPoint ->
                val launcher = entryPoint.fragmentLauncherProvider.invoke()
                launcher.launch(
                    fragmentManager = requireActivity().supportFragmentManager,
                    containerViewId = requireActivity().findViewById<ViewGroup>(R.id.host).id
                )
            }
        )
        binding.internalEntryPointsRecyclerView.adapter = adapter
        val fragmentEntryPoints = internalFeatureApi<Graphics3DApi, Graphics3DInternalApi>()
            .internalEntryPoints
        adapter.submitList(fragmentEntryPoints)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MainFragment"
    }
}
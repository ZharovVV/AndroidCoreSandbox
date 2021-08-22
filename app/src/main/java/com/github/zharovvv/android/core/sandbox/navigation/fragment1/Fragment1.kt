package com.github.zharovvv.android.core.sandbox.navigation.fragment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.databinding.FragmentBaseLayoutBinding

class Fragment1 : Fragment() {

    private var _binding: FragmentBaseLayoutBinding? = null
    private val binding: FragmentBaseLayoutBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            textViewFragment.text = getString(R.string.title_fragment1)
            val navController = findNavController()
//            buttonFragmentBack.setOnClickListener { }
            buttonFragmentNext.setOnClickListener { navController.navigate(R.id.action_fragment1_to_fragment2) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
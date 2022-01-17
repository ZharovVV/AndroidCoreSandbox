package com.github.zharovvv.android.core.sandbox.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.zharovvv.android.core.sandbox.databinding.FragmentExampleBinding

class ExampleFragment : Fragment() {

    companion object {
        const val FRAGMENT_DATA_KEY = "FRAGMENT_DATA_KEY"
        const val FRAGMENT_COLOR_KEY = "FRAGMENT_COLOR_KEY"
        private const val LOG_TAG = "FragmentLifecycle"
    }

    private var _binding: FragmentExampleBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        Log.i(LOG_TAG, "$this#onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "$this#onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(LOG_TAG, "$this#onCreateView")
        val args = requireArguments()
        val colorId : Int = args.getInt(FRAGMENT_COLOR_KEY)
        _binding = FragmentExampleBinding.inflate(inflater, container, false)
        return binding.root.apply {
            setBackgroundColor(
                resources.getColor(
                    colorId,
                    activity?.theme
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "$this#onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentNameTextView.text = tag
        binding.fragmentDataTextView.text = requireArguments().getCharSequence(FRAGMENT_DATA_KEY)
    }

    override fun onStart() {
        Log.i(LOG_TAG, "$this#onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i(LOG_TAG, "$this#onResume")
        super.onResume()
        (activity as FragmentOnResumeListener).fragmentOnResume(tag!!)
    }

    override fun onPause() {
        Log.i(LOG_TAG, "$this#onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i(LOG_TAG, "$this#onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.i(LOG_TAG, "$this#onDestroyView")
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        Log.i(LOG_TAG, "$this#onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.i(LOG_TAG, "$this#onDetach")
        super.onDetach()
    }


}
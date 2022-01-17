package com.github.zharovvv.android.core.sandbox.fragment

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.databinding.ActivityFragmentExampleBinding
import com.github.zharovvv.android.core.sandbox.fragment.ExampleFragment.Companion.FRAGMENT_COLOR_KEY
import com.github.zharovvv.android.core.sandbox.fragment.ExampleFragment.Companion.FRAGMENT_DATA_KEY

class FragmentExampleActivity : LogLifecycleAppCompatActivity(), FragmentOnResumeListener {

    private lateinit var binding: ActivityFragmentExampleBinding
    private lateinit var currentFragmentTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentExampleBinding.inflate(layoutInflater)
        initSpinner(binding.fragmentBackgroundSpinner)
        setContentView(binding.root)
        initAddButton(button = binding.addButton)
        initReplaceButton(button = binding.replaceButton)
        initRemoveButton(button = binding.removeButton)
    }

    private fun initSpinner(spinner: Spinner) {
        spinner.apply {
            adapter = ColorArraySpinnerAdapter(
                context = context,
                objects = listOf(
                    R.color.blue,
                    R.color.purple_200,
                    R.color.purple_500,
                    R.color.purple_700
                )
            )
            prompt = "Fragment background"
            setSelection(0)
        }
    }

    private fun bundleFromBinding(): Bundle {
        val data = binding.initialDataEditText.text.toString()
        val colorId: Int = binding.fragmentBackgroundSpinner.selectedItem as Int
        return bundleOf(
            FRAGMENT_DATA_KEY to data,
            FRAGMENT_COLOR_KEY to colorId
        )
    }

    private fun initAddButton(button: Button) {
        button.setOnClickListener {
            val bundle = bundleFromBinding()
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(
                    R.id.fragment_host,
                    ExampleFragment::class.java,
                    bundle,
                    generateFragmentTag()
                )
                .apply {
                    if (binding.addToBackStackCheckBox.isChecked) {
                        addToBackStack(null)
                    }
                }
                .commit()
        }
    }

    private fun generateFragmentTag(): String {
        val colorId: Int = binding.fragmentBackgroundSpinner.selectedItem as Int
        return "ExampleFragment-$colorId"
    }

    private fun initReplaceButton(button: Button) {
        button.setOnClickListener {
            val bundle = bundleFromBinding()
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ExampleFragment>(
                    containerViewId = R.id.fragment_host,
                    tag = generateFragmentTag(),
                    args = bundle
                )
                if (binding.addToBackStackCheckBox.isChecked) {
                    addToBackStack(null)
                }
            }
        }
    }

    private fun initRemoveButton(button: Button) {
        button.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
            if (fragment != null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    remove(fragment)
                }
            }
        }
    }

    override fun fragmentOnResume(fragmentTag: String) {
        currentFragmentTag = fragmentTag
    }
}

interface FragmentOnResumeListener {
    fun fragmentOnResume(fragmentTag: String)
}
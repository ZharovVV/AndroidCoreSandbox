package com.github.zharovvv.graphics.presentation.fragments

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.zharovvv.core.navigation.ActivityLauncher
import kotlin.reflect.KClass

internal class FragmentLauncher(
    private val fragmentClass: KClass<out Fragment>,
    private val fragmentTag: String
) : ActivityLauncher {

    override fun launch(context: Context) {
        //ну такое
        val activity = context as FragmentActivity
        val containerView = activity
            .findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0) as ViewGroup
        val fragmentManager = activity.supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(containerView.id, fragmentClass.java, null, fragmentTag)
            .addToBackStack(BACKSTACK_NAME)
            .commit()
    }

    private companion object {
        const val BACKSTACK_NAME = "FragmentRouterBackStack"
    }
}
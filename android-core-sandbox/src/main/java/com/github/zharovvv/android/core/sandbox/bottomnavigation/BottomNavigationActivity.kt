package com.github.zharovvv.android.core.sandbox.bottomnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.fragment.ExampleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.github.zharovvv.core.ui.R as DSR

internal class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(
                    R.id.fragment_container,
                    ExampleFragment.newInstance(
                        fragmentColor = DSR.color.ds_blue,
                        fragmentData = "Main"
                    ),
                    "Главная"
                )
            }
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_main -> {
                    if (supportFragmentManager.fragments.last()
                            .let { it.tag == "Главная" && it.isVisible }
                    ) {
                        //Nothing
                    } else {
                        supportFragmentManager.popBackStack()
                    }
                }

                else -> {
                    if (supportFragmentManager.fragments.last()
                            .let { it.tag == (menuItem.title?.toString() ?: "") && it.isVisible }
                    ) {
                        return@setOnItemSelectedListener true
                    }
                    if (supportFragmentManager.backStackEntryCount >= 1) {
                        supportFragmentManager.popBackStack()
                    }
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                            .replace(
                                R.id.fragment_container,
                                ExampleFragment.newInstance(
                                    fragmentColor = DSR.color.ds_purple_200,
                                    fragmentData = menuItem.title?.toString() ?: ""
                                ),
                                menuItem.title?.toString() ?: ""
                            )
                            .addToBackStack(null)
                    }
                }
//                R.id.nav_2 -> {}
//                R.id.nav_3 -> {}
//                R.id.nav_4 -> {}
            }
            true
        }
        val fragmentLifecycleCallbacks = object : FragmentLifecycleCallbacks() {
            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                if (f.tag == "Главная") {
                    bottomNavigationView.selectedItemId = R.id.nav_main
                }
            }
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, false)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    supportFragmentManager.unregisterFragmentLifecycleCallbacks(
                        fragmentLifecycleCallbacks
                    )
                    source.lifecycle.removeObserver(this)
                }
            }
        })
    }
}
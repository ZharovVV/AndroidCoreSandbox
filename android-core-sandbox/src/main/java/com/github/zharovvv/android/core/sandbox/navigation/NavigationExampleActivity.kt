package com.github.zharovvv.android.core.sandbox.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

/**
 * # Navigation AC
 *
 * Рассмотрим простой пример с NavigationExampleActivity и тремя фрагментами:
 * Fragment1, Fragment2, Fragment3. NavigationExampleActivity будет поочередно отображать в себе фрагменты.
 * Для этого нам понадобятся следующие __Navigation инструменты__:
 * * [NavController] - этот основной механизм Navigation Component.
 * Именно его мы будем просить показывать на экране фрагменты.
 * Но чтобы он мог это делать, он должен иметь список фрагментов и контейнер,
 * в котором он будет эти фрагменты отображать.
 * * [NavGraph] - это список фрагментов, который мы будем создавать и наполнять.
 * NavController сможет показывать фрагменты только из этого списка. Далее будем называть его графом.
 * * [NavHostFragment] - это контейнер. Внутри него NavController будет отображать фрагменты.
 * Еще раз, кратко, для понимания: контроллер в контейнере отображает фрагменты из графа.
 */
class NavigationExampleActivity : LogLifecycleAppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_example)
        //Запрашиваем NavController у контейнера (NavHostFragment)
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)    //Не работает для FragmentContainerView
        //так как представление фрагмента недоступно внутри Activity#onCreate
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }
}
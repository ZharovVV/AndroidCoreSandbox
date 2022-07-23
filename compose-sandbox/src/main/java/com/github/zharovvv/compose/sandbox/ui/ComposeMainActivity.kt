package com.github.zharovvv.compose.sandbox.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.zharovvv.compose.sandbox.di.api.internal.ui.diViewModels
import com.github.zharovvv.compose.sandbox.ui.navigation.MainScreenNavComponent
import com.github.zharovvv.compose.sandbox.ui.theme.AppTheme
import kotlinx.coroutines.launch

typealias AndroidColor = android.graphics.Color

/**
 * # Jetpack Compose
 * ## Требования к архитектуре приложения с точки зрения Compose
 * Это требование можно описать формулой:
 * __UI = f( state )__
 * * UI - макет экрана
 * * f(..) - методы компоновки
 * * state - состояние приложения
 *
 * Так в принципе работают почти все декларитивные фреймворки (в т.ч. Compose).
 * Максимально удобно работать с Compose в UDF-архитектурах (MVI, ELM, MVU).
 * UDF - Unidirectional Data Flow (Однонаправленный поток данных).
 */
class ComposeMainActivity : ComponentActivity() {

    private val composeMainViewModel: ComposeMainViewModel by diViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        //TODO если добавить - то контент будет залезать под статус бар
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = AndroidColor.TRANSPARENT
        window.navigationBarColor = AndroidColor.TRANSPARENT
        setContent {
//            //У данной вью модели вызовется onCleared, когда ComposeMainActivity финиширует
//            val anotherComposeViewModel: Compose1ViewModel = viewModel()
//            val navController = rememberNavController()
            AppTheme(isDynamic = true) {
                MainScreenNavComponent()
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.i("ComposeMainActivity-DEBUG", "call repeatOnLifecycle block")
            }
            //вызвалось только после onStop()
            Log.i("ComposeMainActivity-DEBUG", "call after repeatOnLifecycle block")
        }
    }

    // Перенесено в ComposeMainViewModel
//    override fun onDestroy() {
//        super.onDestroy()
//        if (isFinishing) {
//            releaseFeature<ComposeSandboxApi>()
//        }
//    }

    companion object {
        internal fun newIntent(context: Context): Intent =
            Intent(context, ComposeMainActivity::class.java)
    }
}
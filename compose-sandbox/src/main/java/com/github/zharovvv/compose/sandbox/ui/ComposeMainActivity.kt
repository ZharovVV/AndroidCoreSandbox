package com.github.zharovvv.compose.sandbox.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.github.zharovvv.compose.sandbox.di.api.internal.ui.diViewModels
import com.github.zharovvv.compose.sandbox.ui.main.MainScreen2
import com.github.zharovvv.compose.sandbox.ui.theme.AppTheme

typealias AndroidColor = android.graphics.Color

class ComposeMainActivity : ComponentActivity() {

    private val composeMainViewModel: ComposeMainViewModel by diViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defaultViewModelCreationExtras
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        //TODO если добавить - то контент будет залезать под статус бар
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = AndroidColor.TRANSPARENT
        window.navigationBarColor = AndroidColor.TRANSPARENT
        setContent {
            AppTheme(isDynamic = true) {
                MainScreen2()
            }
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
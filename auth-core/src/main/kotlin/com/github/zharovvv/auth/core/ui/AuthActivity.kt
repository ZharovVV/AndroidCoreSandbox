package com.github.zharovvv.auth.core.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.github.zharovvv.auth.core.di.api.AuthCoreApi
import com.github.zharovvv.auth.core.di.api.internal.AuthCoreInternalApi
import com.github.zharovvv.auth.core.ui.login.model.AuthCoreEffect
import com.github.zharovvv.auth.core.ui.navigation.RootNavGraph
import com.github.zharovvv.auth.core.utils.coroutines.launchInWithLifecycle
import com.github.zharovvv.common.di.internalFeatureApi
import com.github.zharovvv.core.ui.compose.theme.AppTheme
import kotlinx.coroutines.flow.onEach
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

typealias AndroidColor = android.graphics.Color

internal class AuthActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels {
        internalFeatureApi<AuthCoreApi, AuthCoreInternalApi>().authViewModel
    }

    private val loginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ::handleLoginResponse
    )

    private val logoutLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { viewModel.onLogoutSuccess() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = AndroidColor.TRANSPARENT
        window.navigationBarColor = AndroidColor.TRANSPARENT
        viewModel.coreEffects
            .onEach { Log.i("AuthCore", "effect: $it") }
            .onEach { effect ->
                when (effect) {
                    is AuthCoreEffect.OpenLoginScreen -> loginLauncher.launch(effect.intent)
                    is AuthCoreEffect.OpenLogoutScreen -> logoutLauncher.launch(effect.intent)
                }
            }
            .launchInWithLifecycle(lifecycle)
        setContent {
            AppTheme {
                RootNavGraph(viewModel = viewModel)
            }
        }
    }

    private fun handleLoginResponse(activityResult: ActivityResult) {
        runCatching {
            val loginResultIntent = activityResult.data
                ?: error("Result of Login is null!")
            AuthorizationException.fromIntent(loginResultIntent)?.let { throw it }
            AuthorizationResponse.fromIntent(loginResultIntent)
                ?.createTokenExchangeRequest()
                ?: error("TokenExchangeRequest is null!")
        }
            .onFailure(viewModel::onAuthCodeRequestFailed)
            .onSuccess(viewModel::onAuthCodeRequestSuccess)
    }

    companion object {
        internal fun newIntent(context: Context): Intent =
            Intent(context, AuthActivity::class.java)
    }
}
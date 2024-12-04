package com.github.zharovvv.auth.core.ui

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Stable
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zharovvv.auth.core.di.api.AuthCoreApi
import com.github.zharovvv.auth.core.domain.model.AuthServiceConfig
import com.github.zharovvv.auth.core.domain.model.Token
import com.github.zharovvv.auth.core.domain.model.TokenStatus
import com.github.zharovvv.auth.core.domain.usecase.GetAuthCodeRequestUseCase
import com.github.zharovvv.auth.core.domain.usecase.GetTokenRequestUseCase
import com.github.zharovvv.auth.core.domain.usecase.GetTokenStatusUseCase
import com.github.zharovvv.auth.core.domain.usecase.LogoutUseCase
import com.github.zharovvv.auth.core.domain.usecase.SaveTokenUseCase
import com.github.zharovvv.auth.core.ui.login.model.AuthCoreEffect
import com.github.zharovvv.auth.core.ui.login.model.AuthNavEffect
import com.github.zharovvv.auth.core.ui.login.model.AuthUiState
import com.github.zharovvv.auth.core.ui.login.model.PreLoginEffect
import com.github.zharovvv.auth.core.utils.coroutines.cancellableRunCatching
import com.github.zharovvv.common.di.releaseFeature
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest
import net.openid.appauth.TokenResponse
import java.io.Closeable
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Stable
internal class AuthViewModel(
    private val authorizationService: AuthorizationService,
    private val getTokenStatusUseCase: GetTokenStatusUseCase,
    private val getAuthCodeRequestUseCase: GetAuthCodeRequestUseCase,
    private val getTokenRequestUseCase: GetTokenRequestUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel(
    Closeable { authorizationService.dispose() },
    Closeable { releaseFeature<AuthCoreApi>() }
) {

    val state: StateFlow<AuthUiState> get() = _state
    private val _state: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Loading)
    val navEffects: Flow<AuthNavEffect> get() = _navEffectsChannel.receiveAsFlow()
    private val _navEffectsChannel = Channel<AuthNavEffect>(BUFFERED)
    val coreEffects: Flow<AuthCoreEffect> get() = _coreEffectsChannel.receiveAsFlow()
    private val _coreEffectsChannel = Channel<AuthCoreEffect>(BUFFERED)
    val preLoginEffect: Flow<PreLoginEffect> get() = _preLoginEffectsChannel.receiveAsFlow()
    private val _preLoginEffectsChannel = Channel<PreLoginEffect>(BUFFERED)

    fun init() {
        viewModelScope.launch {
            when (getTokenStatusUseCase()) {
                TokenStatus.ACTIVE -> {
                    _navEffectsChannel.send(AuthNavEffect.OpenGitHubProfileScreen)
                }

                TokenStatus.EXPIRED -> {
                    /*Не нужно для GitHub*/
                }

                TokenStatus.NONE -> {
                    _state.update {
                        AuthUiState.Login(loginButtonText = LOGIN_BUTTON_TEXT)
                    }
                }
            }
        }
    }

    fun onLoginButtonClick() {
        viewModelScope.launch {
            _state.update { AuthUiState.Loading }
            val authCodeRequestObject = getAuthCodeRequestUseCase()
            _coreEffectsChannel.send(
                with(authCodeRequestObject) {
                    AuthCoreEffect.OpenLoginScreen(
                        intent = authorizationService.getAuthorizationRequestIntent(
                            AuthorizationRequest.Builder(
                                authServiceConfig.toLibConfig(),
                                clientId,
                                responseType,
                                Uri.parse(redirectEndpoint)
                            )
                                .setScope(scope)
                                .build(),
                            CustomTabsIntent.Builder().build()
                        )
                    )
                }
            )
        }
    }

    fun onAuthCodeRequestSuccess(tokenRequest: TokenRequest) {
        viewModelScope.launch {
            _state.update { AuthUiState.Loading }
            cancellableRunCatching {
                val tokenRequestObj = getTokenRequestUseCase()
                val token = authorizationService.getToken(
                    tokenRequest = tokenRequest,
                    clientAuthentication = ClientSecretPost(tokenRequestObj.clientSecret)
                )
                saveTokenUseCase(
                    Token(
                        accessToken = token.accessToken.orEmpty(),
                        refreshToken = token.refreshToken
                    )
                )
            }
                .onSuccess { _navEffectsChannel.send(AuthNavEffect.OpenGitHubProfileScreen) }
                .onFailure {
                    _state.update { AuthUiState.Login(LOGIN_BUTTON_TEXT) }
                    _preLoginEffectsChannel.send(
                        PreLoginEffect.ShowError(
                            it.message ?: DEFAULT_ERROR_MESSAGE
                        )
                    )
                }

        }
    }

    fun onAuthCodeRequestFailed(error: Throwable) {
        Log.e("AuthCore", "Ошибка при получении Auth Code", error)
        viewModelScope.launch {
            _state.update { AuthUiState.Login(LOGIN_BUTTON_TEXT) }
            _preLoginEffectsChannel.send(
                PreLoginEffect.ShowError(
                    error.message ?: DEFAULT_ERROR_MESSAGE
                )
            )
        }
    }

    fun onLogoutButtonClick() {
        viewModelScope.launch {
            _state.update { AuthUiState.Loading }
            val logoutRequest = logoutUseCase()
            _coreEffectsChannel.send(
                AuthCoreEffect.OpenLogoutScreen(
                    intent = authorizationService.getEndSessionRequestIntent(
                        EndSessionRequest.Builder(logoutRequest.authServiceConfig.toLibConfig())
                            .setIdTokenHint(logoutRequest.idToken)
                            .setPostLogoutRedirectUri(logoutRequest.logoutCallbackUri.toUri())
                            .build(),
                        CustomTabsIntent.Builder().build()
                    )
                )
            )
        }
    }

    fun onLogoutSuccess() {
        viewModelScope.launch {
            _state.update { AuthUiState.Login(LOGIN_BUTTON_TEXT) }
            _navEffectsChannel.send(AuthNavEffect.OpenPreLoginScreen)
        }
    }

    private suspend fun AuthorizationService.getToken(
        tokenRequest: TokenRequest,
        clientAuthentication: ClientAuthentication
    ): TokenResponse {
        return suspendCancellableCoroutine { cont ->
            performTokenRequest(tokenRequest, clientAuthentication) { tokenRs, exception ->
                if (tokenRs != null) {
                    cont.resume(tokenRs)
                } else {
                    cont.resumeWithException(
                        exception ?: IllegalStateException("Could not get token!")
                    )
                }
            }
        }
    }

    private fun AuthServiceConfig.toLibConfig() = AuthorizationServiceConfiguration(
        Uri.parse(authEndpoint),
        Uri.parse(tokenEndpoint),
        registrationEndpoint?.let(Uri::parse),
        Uri.parse(endSessionEndpoint)
    )


    private companion object {
        const val LOGIN_BUTTON_TEXT = "Login GitHub"
        const val DEFAULT_ERROR_MESSAGE = "Произошла непредвиденная ошибка"
    }
}
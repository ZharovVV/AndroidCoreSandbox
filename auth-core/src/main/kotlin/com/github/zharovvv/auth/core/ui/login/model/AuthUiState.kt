package com.github.zharovvv.auth.core.ui.login.model

import androidx.compose.runtime.Stable

@Stable
internal sealed interface AuthUiState {

    data object Loading : AuthUiState

    data class Login(
        val loginButtonText: String
    ) : AuthUiState
}
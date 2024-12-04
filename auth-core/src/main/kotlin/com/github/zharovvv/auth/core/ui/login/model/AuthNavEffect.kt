package com.github.zharovvv.auth.core.ui.login.model

import android.content.Intent

internal sealed interface AuthNavEffect {

    data object OpenGitHubProfileScreen : AuthNavEffect

    data object OpenPreLoginScreen : AuthNavEffect
}

internal sealed interface PreLoginEffect {
    data class ShowError(
        val message: String
    ) : PreLoginEffect
}

internal sealed interface AuthCoreEffect {
    data class OpenLoginScreen(
        val intent: Intent
    ) : AuthCoreEffect

    data class OpenLogoutScreen(
        val intent: Intent
    ) : AuthCoreEffect
}
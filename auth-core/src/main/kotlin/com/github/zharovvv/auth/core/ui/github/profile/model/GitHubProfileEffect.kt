package com.github.zharovvv.auth.core.ui.github.profile.model

internal sealed interface GitHubProfileEffect {

    data class ShowError(val message: String) : GitHubProfileEffect
}
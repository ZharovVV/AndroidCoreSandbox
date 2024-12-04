package com.github.zharovvv.auth.core.ui.github.profile.model

import androidx.compose.runtime.Stable
import com.github.zharovvv.auth.core.domain.model.GitHubProfileInfo

@Stable
internal sealed interface GitHubProfileUiState {

    data object Loading : GitHubProfileUiState

    data class Loaded(
        val login: String,
        val avatarUrl: String?,
        val email: String,
        val location: String
    ) : GitHubProfileUiState
}

internal fun GitHubProfileInfo.toUi() = GitHubProfileUiState.Loaded(
    login = login,
    avatarUrl = avatarUrl,
    email = email.orEmpty(),
    location = location.orEmpty()
)
package com.github.zharovvv.auth.core.domain.model

data class GitHubProfileInfo(
    val login: String,
    val avatarUrl: String? = null,
    val email: String? = null,
    val location: String? = null
)

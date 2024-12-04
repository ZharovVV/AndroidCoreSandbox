package com.github.zharovvv.auth.core.domain.repository

import androidx.annotation.AnyThread
import com.github.zharovvv.auth.core.domain.model.GitHubProfileInfo
import com.github.zharovvv.auth.core.domain.model.Token

internal interface GitHubProfileInfoRepository {

    @AnyThread
    suspend fun getGitHubProfileInfo(token: Token): GitHubProfileInfo
}
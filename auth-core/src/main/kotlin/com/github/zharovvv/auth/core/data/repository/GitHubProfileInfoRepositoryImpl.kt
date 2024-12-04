package com.github.zharovvv.auth.core.data.repository

import com.github.zharovvv.auth.core.data.model.toDomain
import com.github.zharovvv.auth.core.data.remote.GitHubProfileInfoRemoteStorage
import com.github.zharovvv.auth.core.domain.model.GitHubProfileInfo
import com.github.zharovvv.auth.core.domain.model.Token
import com.github.zharovvv.auth.core.domain.repository.GitHubProfileInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GitHubProfileInfoRepositoryImpl(
    private val gitHubProfileInfoRemoteStorage: GitHubProfileInfoRemoteStorage
) : GitHubProfileInfoRepository {
    override suspend fun getGitHubProfileInfo(token: Token): GitHubProfileInfo =
        withContext(Dispatchers.IO) {
            gitHubProfileInfoRemoteStorage.getGitHubProfileInfo(token).toDomain()
        }
}
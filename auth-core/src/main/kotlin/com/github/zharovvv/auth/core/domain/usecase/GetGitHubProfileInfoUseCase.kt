package com.github.zharovvv.auth.core.domain.usecase

import com.github.zharovvv.auth.core.domain.model.GitHubProfileInfo
import com.github.zharovvv.auth.core.domain.repository.AuthRepository
import com.github.zharovvv.auth.core.domain.repository.GitHubProfileInfoRepository

class GetGitHubProfileInfoUseCase internal constructor(
    private val authRepository: AuthRepository,
    private val gitHubProfileInfoRepository: GitHubProfileInfoRepository
) {

    suspend operator fun invoke(): GitHubProfileInfo =
        gitHubProfileInfoRepository.getGitHubProfileInfo(
            token = requireNotNull(authRepository.getToken()) { "Token is null!" }
        )
}
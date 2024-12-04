package com.github.zharovvv.auth.core.domain.usecase

import com.github.zharovvv.auth.core.domain.model.TokenStatus
import com.github.zharovvv.auth.core.domain.repository.AuthRepository

class GetTokenStatusUseCase internal constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): TokenStatus = when (authRepository.getToken()) {
        null -> TokenStatus.NONE
        else -> TokenStatus.ACTIVE
    }
}
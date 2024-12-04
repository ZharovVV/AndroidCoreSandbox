package com.github.zharovvv.auth.core.domain.usecase

import com.github.zharovvv.auth.core.domain.model.LogoutRequestObject
import com.github.zharovvv.auth.core.domain.repository.AuthRepository

class LogoutUseCase internal constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): LogoutRequestObject {
        authRepository.clearToken()
        return authRepository.getLogoutRequestObject()
    }
}
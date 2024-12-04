package com.github.zharovvv.auth.core.domain.usecase

import com.github.zharovvv.auth.core.domain.model.AuthCodeRequestObject
import com.github.zharovvv.auth.core.domain.repository.AuthRepository

class GetAuthCodeRequestUseCase internal constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): AuthCodeRequestObject =
        authRepository.getAuthCodeRequestObject()
}
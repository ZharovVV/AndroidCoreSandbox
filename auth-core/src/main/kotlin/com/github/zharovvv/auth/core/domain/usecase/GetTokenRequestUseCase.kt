package com.github.zharovvv.auth.core.domain.usecase

import com.github.zharovvv.auth.core.domain.model.TokenRequestObject
import com.github.zharovvv.auth.core.domain.repository.AuthRepository

class GetTokenRequestUseCase internal constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): TokenRequestObject = authRepository.getTokenRequestObject()
}
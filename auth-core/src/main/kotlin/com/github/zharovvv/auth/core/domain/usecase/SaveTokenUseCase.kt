package com.github.zharovvv.auth.core.domain.usecase

import com.github.zharovvv.auth.core.domain.model.Token
import com.github.zharovvv.auth.core.domain.repository.AuthRepository

class SaveTokenUseCase internal constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(token: Token) {
        authRepository.saveToken(token)
    }
}
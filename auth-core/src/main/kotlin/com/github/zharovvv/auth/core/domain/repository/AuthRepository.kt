package com.github.zharovvv.auth.core.domain.repository

import com.github.zharovvv.auth.core.domain.model.AuthCodeRequestObject
import com.github.zharovvv.auth.core.domain.model.LogoutRequestObject
import com.github.zharovvv.auth.core.domain.model.Token
import com.github.zharovvv.auth.core.domain.model.TokenRequestObject

internal interface AuthRepository {

    suspend fun getAuthCodeRequestObject(): AuthCodeRequestObject

    suspend fun getTokenRequestObject(): TokenRequestObject

    suspend fun getLogoutRequestObject(): LogoutRequestObject

    suspend fun getToken(): Token?

    suspend fun saveToken(token: Token)

    suspend fun clearToken()
}
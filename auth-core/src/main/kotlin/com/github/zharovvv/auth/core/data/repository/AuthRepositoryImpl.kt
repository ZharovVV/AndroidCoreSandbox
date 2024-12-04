package com.github.zharovvv.auth.core.data.repository

import com.github.zharovvv.auth.core.data.local.CredentialsLocalStorage
import com.github.zharovvv.auth.core.domain.model.AuthCodeRequestObject
import com.github.zharovvv.auth.core.domain.model.AuthServiceConfig
import com.github.zharovvv.auth.core.domain.model.LogoutRequestObject
import com.github.zharovvv.auth.core.domain.model.Token
import com.github.zharovvv.auth.core.domain.model.TokenRequestObject
import com.github.zharovvv.auth.core.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.openid.appauth.ResponseTypeValues

internal class AuthRepositoryImpl(
    private val credentialsLocalStorage: CredentialsLocalStorage
) : AuthRepository {

    private val authCodeRequestObject = AuthCodeRequestObject(
        authServiceConfig = AuthServiceConfigImpl,
        clientId = credentialsLocalStorage.clientId,
        responseType = ResponseTypeValues.CODE,
        redirectEndpoint = "com.github.zharovvv.auth://github.com/callback",
        scope = "user,repo"
    )

    private val tokenRequestObject = TokenRequestObject(
        clientSecret = credentialsLocalStorage.clientSecret
    )

    private val logoutRequestObject = LogoutRequestObject(
        authServiceConfig = AuthServiceConfigImpl,
        logoutCallbackUri = "com.github.zharovvv.auth://github.com/logout/callback"
    )

    override suspend fun getAuthCodeRequestObject(): AuthCodeRequestObject = authCodeRequestObject

    override suspend fun getTokenRequestObject(): TokenRequestObject = tokenRequestObject
    override suspend fun getLogoutRequestObject(): LogoutRequestObject = logoutRequestObject

    override suspend fun getToken(): Token? = withContext(Dispatchers.IO) {
        credentialsLocalStorage.getToken()
    }

    override suspend fun saveToken(token: Token) = withContext(Dispatchers.IO) {
        credentialsLocalStorage.saveToken(token)
    }

    override suspend fun clearToken() = withContext(Dispatchers.IO) {
        credentialsLocalStorage.clearToken()
    }

    private object AuthServiceConfigImpl : AuthServiceConfig {
        override val authEndpoint: String = "https://github.com/login/oauth/authorize"
        override val tokenEndpoint: String = "https://github.com/login/oauth/access_token"
        override val registrationEndpoint: String? = null
        override val endSessionEndpoint: String = "https://github.com/logout"
    }
}
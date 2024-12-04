package com.github.zharovvv.auth.core.domain.model

data class AuthCodeRequestObject(
    val authServiceConfig: AuthServiceConfig,
    val clientId: String,
    val responseType: String,
    val redirectEndpoint: String,
    val scope: String?
)

data class TokenRequestObject(
    val clientSecret: String
)

data class LogoutRequestObject(
    val authServiceConfig: AuthServiceConfig,
    val logoutCallbackUri: String,
    val idToken: String? = null
)

interface AuthServiceConfig {
    val authEndpoint: String
    val tokenEndpoint: String
    val registrationEndpoint: String?
    val endSessionEndpoint: String
}

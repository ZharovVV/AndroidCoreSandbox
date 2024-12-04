package com.github.zharovvv.auth.core.domain.model

//Так не безопасно, почитай про Frida.
data class Token(
    val accessToken: String,
    val refreshToken: String?
)

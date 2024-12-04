package com.github.zharovvv.auth.core.data.local

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.github.zharovvv.auth.core.BuildConfig
import com.github.zharovvv.auth.core.domain.model.Token

internal class CredentialsLocalStorage(
    private val sharedPreferences: SharedPreferences
) {

    val clientId: String = BuildConfig.CLIENT_ID
    val clientSecret: String = BuildConfig.CLIENT_SECRET

    @WorkerThread
    fun getToken(): Token? {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        return accessToken?.let {
            Token(
                accessToken = it,
                refreshToken = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
            )
        }
    }

    @WorkerThread
    fun saveToken(token: Token) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, token.accessToken)
            putString(REFRESH_TOKEN_KEY, token.refreshToken)
        }
    }

    @WorkerThread
    fun clearToken() {
        sharedPreferences.edit {
            remove(ACCESS_TOKEN_KEY)
            remove(REFRESH_TOKEN_KEY)
        }
    }

    private companion object {
        const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }
}
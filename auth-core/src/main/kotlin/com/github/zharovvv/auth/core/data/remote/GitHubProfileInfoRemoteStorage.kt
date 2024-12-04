package com.github.zharovvv.auth.core.data.remote

import androidx.annotation.WorkerThread
import com.github.zharovvv.auth.core.data.model.GitHubProfileInfoDTO
import com.github.zharovvv.auth.core.domain.model.Token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.http.headers

internal class GitHubProfileInfoRemoteStorage(
    private val httpClient: HttpClient
) {

    @WorkerThread
    suspend fun getGitHubProfileInfo(token: Token): GitHubProfileInfoDTO {
        return httpClient.get(url = Url("https://api.github.com/user")) {
            headers {
                header(HttpHeaders.Accept, "application/vnd.github+json")
                bearerAuth(token.accessToken)
                header("X-GitHub-Api-Version", "2022-11-28")
            }
        }.body<GitHubProfileInfoDTO>()
    }
}
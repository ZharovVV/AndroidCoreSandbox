package com.github.zharovvv.auth.core.di.api.internal

import android.content.Context
import android.util.Log
import com.github.zharovvv.auth.core.data.local.CredentialsLocalStorage
import com.github.zharovvv.auth.core.data.remote.GitHubProfileInfoRemoteStorage
import com.github.zharovvv.auth.core.data.repository.AuthRepositoryImpl
import com.github.zharovvv.auth.core.data.repository.GitHubProfileInfoRepositoryImpl
import com.github.zharovvv.auth.core.domain.repository.AuthRepository
import com.github.zharovvv.auth.core.domain.repository.GitHubProfileInfoRepository
import com.github.zharovvv.auth.core.domain.usecase.GetAuthCodeRequestUseCase
import com.github.zharovvv.auth.core.domain.usecase.GetGitHubProfileInfoUseCase
import com.github.zharovvv.auth.core.domain.usecase.GetTokenRequestUseCase
import com.github.zharovvv.auth.core.domain.usecase.GetTokenStatusUseCase
import com.github.zharovvv.auth.core.domain.usecase.LogoutUseCase
import com.github.zharovvv.auth.core.domain.usecase.SaveTokenUseCase
import com.github.zharovvv.auth.core.ui.AuthViewModel
import com.github.zharovvv.auth.core.ui.github.profile.GitHubProfileViewModel
import com.github.zharovvv.auth.core.utils.vm.ViewModelFactory
import com.github.zharovvv.common.di.qualifier.ApplicationContext
import com.github.zharovvv.common.di.scope.PerFeature
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import net.openid.appauth.AuthorizationService

@Module(
    includes = [
        DataModule::class,
        DomainModule::class,
        UiModule::class
    ]
)
internal class AuthCoreInternalModule {

    @Provides
    @PerFeature
    fun provideAuthorizationService(
        @ApplicationContext context: Context
    ): AuthorizationService = AuthorizationService(context)
}

@Module
internal class DataModule {

    @Provides
    @PerFeature
    fun provideCredentialsLocalStorage(
        @ApplicationContext applicationContext: Context
    ): CredentialsLocalStorage {
        val sp = applicationContext.getSharedPreferences("AUTH_SHARED_PREF", Context.MODE_PRIVATE)
        return CredentialsLocalStorage(sp)
    }

    @Provides
    @PerFeature
    fun provideAuthRepository(
        credentialsLocalStorage: CredentialsLocalStorage
    ): AuthRepository = AuthRepositoryImpl(credentialsLocalStorage)

    @Provides
    @PerFeature
    fun provideGitHubProfileInfoRemoteStorage() = GitHubProfileInfoRemoteStorage(
        httpClient = HttpClient(Android) {
            val timeout = 6_000L
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        useAlternativeNames = true
                        ignoreUnknownKeys = true
                        encodeDefaults = false
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = timeout
                connectTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    )

    @Provides
    @PerFeature
    fun provideGitHubProfileInfoRepository(
        gitHubProfileInfoRemoteStorage: GitHubProfileInfoRemoteStorage
    ): GitHubProfileInfoRepository = GitHubProfileInfoRepositoryImpl(
        gitHubProfileInfoRemoteStorage
    )
}

@Module
internal class DomainModule {

    @Provides
    @PerFeature
    fun provideGetAuthCodeRequestUseCase(
        authRepository: AuthRepository
    ) = GetAuthCodeRequestUseCase(authRepository)

    @Provides
    @PerFeature
    fun provideGetTokenRequestUseCase(
        authRepository: AuthRepository
    ) = GetTokenRequestUseCase(authRepository)

    @Provides
    @PerFeature
    fun provideGetTokenStatusUseCase(
        authRepository: AuthRepository
    ) = GetTokenStatusUseCase(authRepository)

    @Provides
    @PerFeature
    fun provideLogoutUseCase(
        authRepository: AuthRepository
    ) = LogoutUseCase(authRepository)

    @Provides
    @PerFeature
    fun provideSaveTokenUseCase(
        authRepository: AuthRepository
    ) = SaveTokenUseCase(authRepository)

    @Provides
    @PerFeature
    fun provideGetGitHubProfileInfoUseCase(
        authRepository: AuthRepository,
        gitHubProfileInfoRepository: GitHubProfileInfoRepository
    ) = GetGitHubProfileInfoUseCase(authRepository, gitHubProfileInfoRepository)

}

@Module
internal class UiModule {

    @Provides
    @PerFeature
    fun provideAuthViewModelFactory(
        authorizationService: AuthorizationService,
        getTokenStatusUseCase: GetTokenStatusUseCase,
        getTokenRequestUseCase: GetTokenRequestUseCase,
        getAuthCodeRequestUseCase: GetAuthCodeRequestUseCase,
        logoutUseCase: LogoutUseCase,
        saveTokenUseCase: SaveTokenUseCase
    ): ViewModelFactory<AuthViewModel> =
        ViewModelFactory(
            create = {
                AuthViewModel(
                    authorizationService = authorizationService,
                    getTokenStatusUseCase = getTokenStatusUseCase,
                    getAuthCodeRequestUseCase = getAuthCodeRequestUseCase,
                    getTokenRequestUseCase = getTokenRequestUseCase,
                    saveTokenUseCase = saveTokenUseCase,
                    logoutUseCase = logoutUseCase
                )
            },
            onCreateHook = AuthViewModel::init
        )

    @Provides
    @PerFeature
    fun provideGitHubProfileViewModelFactory(
        getGitHubProfileInfoUseCase: GetGitHubProfileInfoUseCase
    ): ViewModelFactory<GitHubProfileViewModel> =
        ViewModelFactory(
            create = {
                GitHubProfileViewModel(
                    getGitHubProfileInfoUseCase
                )
            },
            onCreateHook = GitHubProfileViewModel::init
        )

}
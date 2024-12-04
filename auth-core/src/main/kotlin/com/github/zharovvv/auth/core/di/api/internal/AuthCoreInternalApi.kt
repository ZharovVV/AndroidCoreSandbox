package com.github.zharovvv.auth.core.di.api.internal

import com.github.zharovvv.auth.core.di.api.AuthCoreApi
import com.github.zharovvv.auth.core.ui.AuthViewModel
import com.github.zharovvv.auth.core.ui.github.profile.GitHubProfileViewModel
import com.github.zharovvv.auth.core.utils.vm.ViewModelFactory

internal interface AuthCoreInternalApi : AuthCoreApi {

    val authViewModel: ViewModelFactory<AuthViewModel>

    val gitHubProfileViewModel: ViewModelFactory<GitHubProfileViewModel>
}
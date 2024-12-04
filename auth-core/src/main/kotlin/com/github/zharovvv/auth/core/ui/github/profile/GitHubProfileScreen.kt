package com.github.zharovvv.auth.core.ui.github.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.github.zharovvv.auth.core.di.api.AuthCoreApi
import com.github.zharovvv.auth.core.di.api.internal.AuthCoreInternalApi
import com.github.zharovvv.auth.core.ui.AuthViewModel
import com.github.zharovvv.auth.core.ui.github.profile.model.GitHubProfileEffect
import com.github.zharovvv.auth.core.ui.github.profile.model.GitHubProfileUiState
import com.github.zharovvv.common.di.internalFeatureApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GitHubProfileScreen(
    authViewModel: AuthViewModel,
    gitHubProfileViewModel: GitHubProfileViewModel = viewModel(
        factory = internalFeatureApi<AuthCoreApi, AuthCoreInternalApi>().gitHubProfileViewModel
    ),
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GitHub Profile") },
                actions = {
                    IconButton(onClick = authViewModel::onLogoutButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar {
                    Text(it.visuals.message)
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            val state by gitHubProfileViewModel.state.collectAsStateWithLifecycle()
            Content(state = state)
        }
    }
    LaunchedEffect(Unit) {
        gitHubProfileViewModel.effects.collect { effect ->
            when (effect) {
                is GitHubProfileEffect.ShowError -> launch { snackbarHostState.showSnackbar(effect.message) }
            }
        }
    }
}

@Composable
private fun BoxScope.Content(
    state: GitHubProfileUiState
) {
    when (state) {
        GitHubProfileUiState.Loading -> {
            CircularProgressIndicator()
        }

        is GitHubProfileUiState.Loaded -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                state.avatarUrl?.let {
                    AsyncImage(model = it, contentDescription = null)
                }
                Text(text = state.login, style = MaterialTheme.typography.titleLarge)
                Text(text = state.email, style = MaterialTheme.typography.bodySmall)
                Text(text = state.location, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
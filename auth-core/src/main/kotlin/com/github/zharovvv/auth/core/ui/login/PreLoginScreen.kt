package com.github.zharovvv.auth.core.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.zharovvv.auth.core.ui.AuthViewModel
import com.github.zharovvv.auth.core.ui.login.model.AuthUiState
import com.github.zharovvv.auth.core.ui.login.model.PreLoginEffect.ShowError
import kotlinx.coroutines.launch

@Composable
internal fun PreLoginScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
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
            val state by viewModel.state.collectAsStateWithLifecycle()
            Content(
                state = state,
                onLoginButtonClick = viewModel::onLoginButtonClick
            )
        }
    }
    LaunchedEffect(Unit) {
        viewModel.preLoginEffect.collect {
            when (it) {
                is ShowError -> launch { snackbarHostState.showSnackbar(message = it.message) }
            }
        }
    }
}

@Composable
private fun Content(
    state: AuthUiState,
    onLoginButtonClick: () -> Unit
) {
    when (state) {
        AuthUiState.Loading -> {
            CircularProgressIndicator()
        }

        is AuthUiState.Login -> {
            Button(onClick = onLoginButtonClick) {
                Text(text = state.loginButtonText)
            }
        }
    }
}
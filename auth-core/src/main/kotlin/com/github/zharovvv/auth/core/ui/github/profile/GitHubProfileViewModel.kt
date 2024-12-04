package com.github.zharovvv.auth.core.ui.github.profile

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zharovvv.auth.core.domain.usecase.GetGitHubProfileInfoUseCase
import com.github.zharovvv.auth.core.ui.github.profile.model.GitHubProfileEffect
import com.github.zharovvv.auth.core.ui.github.profile.model.GitHubProfileUiState
import com.github.zharovvv.auth.core.ui.github.profile.model.toUi
import com.github.zharovvv.auth.core.utils.coroutines.cancellableRunCatching
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Stable
internal class GitHubProfileViewModel(
    private val getGitHubProfileInfoUseCase: GetGitHubProfileInfoUseCase
) : ViewModel() {

    val state: StateFlow<GitHubProfileUiState> get() = _state
    private val _state: MutableStateFlow<GitHubProfileUiState> =
        MutableStateFlow(GitHubProfileUiState.Loading)
    val effects: Flow<GitHubProfileEffect> get() = _effectsChannel.receiveAsFlow()
    private val _effectsChannel: Channel<GitHubProfileEffect> = Channel(BUFFERED)

    fun init() {
        viewModelScope.launch {
            cancellableRunCatching { getGitHubProfileInfoUseCase().toUi() }
                .onSuccess { _state.value = it }
                .onFailure {
                    Log.e("AuthCore", "error", it)
                    _effectsChannel.send(
                        GitHubProfileEffect.ShowError(
                            it.message ?: "Internal Error"
                        )
                    )
                }
        }
    }
}
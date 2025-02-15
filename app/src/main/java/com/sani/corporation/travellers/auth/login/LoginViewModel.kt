package com.sani.corporation.travellers.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sani.corporation.travellers.data.AuthRepository
import com.sani.corporation.travellers.data.source.models.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

data class LoginUiState (
    val username: String = "",
    val password: String = "",
    val loginButtonEnabled: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authRepository: AuthRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState


    fun updateUsername(newUsername: String) {
        _uiState.update {
            it.copy(username = newUsername)
        }
        checkInfoToEnableButton()
    }

    fun updatePassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
        checkInfoToEnableButton()
    }

    fun onClickLogin() {
        viewModelScope.launch {
            authRepository.login(LoginRequest(_uiState.value.username, _uiState.value.password)).collect()
        }
    }

    private fun checkInfoToEnableButton() {
        _uiState.update {
            it.copy(loginButtonEnabled = uiState.value.password.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.value.username).matches())
        }
    }
}
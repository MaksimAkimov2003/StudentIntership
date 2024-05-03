package ru.hits.studentintership.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hits.studentintership.login.domain.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            LoginScreenState(
                isLoading = false,
                email = "",
                password = ""
            )
        )

    val state = _state.asStateFlow()

    private val _action = MutableSharedFlow<LoginScreenAction>()
    val action = _action.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L)
    )

    fun onIntent(intent: LoginScreenIntent) {
        when (intent) {
            is LoginScreenIntent.UpdateEmailText -> {
                _state.update { it.copy(email = intent.newValue) }
            }

            is LoginScreenIntent.UpdatePasswordText -> {
                _state.update {
                    it.copy(password = intent.newValue)
                }
            }

            is LoginScreenIntent.LoginClick -> {
                viewModelScope.launch {
                    val loginResult = loginUseCase(
                        email = _state.value.email,
                        password = _state.value.password
                    )

                    loginResult.onSuccess {
                        _action.emit(LoginScreenAction.NavigateToNextScreen)
                    }

                    loginResult.onFailure {
                        _action.emit(LoginScreenAction.ShowSnackbar(it.message ?: "Ошибка входа"))
                    }
                }
            }
        }
    }
}
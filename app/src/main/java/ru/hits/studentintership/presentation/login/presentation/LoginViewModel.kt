package ru.hits.studentintership.presentation.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hits.studentintership.common.data.model.LoginBodyDto
import ru.hits.studentintership.common.data.shared.TokenManager
import ru.hits.studentintership.core.Event
import ru.hits.studentintership.core.EventQueue
import ru.hits.studentintership.data.other.OtherService
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val otherService: OtherService,
    private val tokenManager: TokenManager,
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

    val screenEvents: EventQueue = EventQueue()

    fun offerEvent(event: Event) {
        screenEvents.offerEvent(event)
    }

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email,
            )
        }
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(
                password = password,
            )
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        try {
            val loginResponse = otherService.login(LoginBodyDto(email, password))
            tokenManager.saveToken(loginResponse.string())

            offerEvent(LoginScreenEvent.NavigateToNextScreen)
        } catch (e: Exception) {
            offerEvent(LoginScreenEvent.ShowSnackbar("Ошибка авторизации"))
        }
    }
}
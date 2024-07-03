package ru.hits.studentintership.presentation.login.presentation

import ru.hits.studentintership.core.Event

sealed class LoginScreenEvent: Event {
    data class ShowSnackbar(val message: String) : LoginScreenEvent()
    data object NavigateToNextScreen : LoginScreenEvent()
}

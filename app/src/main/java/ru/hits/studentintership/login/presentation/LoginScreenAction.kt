package ru.hits.studentintership.login.presentation

sealed class LoginScreenAction {
    data class ShowSnackbar(val message: String) : LoginScreenAction()
    data object NavigateToNextScreen : LoginScreenAction()
}

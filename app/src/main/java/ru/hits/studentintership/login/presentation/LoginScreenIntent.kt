package ru.hits.studentintership.login.presentation

sealed class LoginScreenIntent {
    data class UpdateEmailText(val newValue: String) : LoginScreenIntent()
    data class UpdatePasswordText(val newValue: String) : LoginScreenIntent()
    data object LoginClick : LoginScreenIntent()
}
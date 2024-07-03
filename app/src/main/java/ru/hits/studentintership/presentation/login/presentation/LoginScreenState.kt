package ru.hits.studentintership.presentation.login.presentation

data class LoginScreenState(
    val isLoading: Boolean,
    val email: String,
    val password: String,
)

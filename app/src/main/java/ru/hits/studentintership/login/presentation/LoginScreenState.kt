package ru.hits.studentintership.login.presentation

data class LoginScreenState(
    val isLoading: Boolean,
    val email: String,
    val password: String,
)

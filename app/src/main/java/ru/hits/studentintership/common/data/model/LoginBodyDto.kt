package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginBodyDto(
    val email: String,
    val password: String,
)

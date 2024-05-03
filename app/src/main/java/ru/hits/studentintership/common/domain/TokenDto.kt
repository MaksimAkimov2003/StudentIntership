package ru.hits.studentintership.common.domain

data class TokenDto(
    val access: String,
    val refresh: String
)

package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    val access: String,
)

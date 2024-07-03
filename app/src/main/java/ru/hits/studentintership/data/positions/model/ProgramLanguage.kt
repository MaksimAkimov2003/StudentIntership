package ru.hits.studentintership.data.positions.model

import kotlinx.serialization.Serializable

@Serializable
data class ProgramLanguage(
    val id: String,
    val name: String
)
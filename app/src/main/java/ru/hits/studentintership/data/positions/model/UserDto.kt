package ru.hits.studentintership.data.positions.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String,
    val fullName: String,
    val group: Group?,
    val id: String,
    val isActive: Boolean?,
    val roles: List<String>,
    val status: String?,
    val currentPractice: PracticeDto?,
)
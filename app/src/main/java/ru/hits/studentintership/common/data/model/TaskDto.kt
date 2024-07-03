package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable
import ru.hits.studentintership.data.positions.model.UserDto

@Serializable
data class TaskDto(
    val author: UserDto,
    val createdAt: String,
    val deadline: String,
    val description: String?,
    val files: List<File>,
    val id: String,
    val name: String
)
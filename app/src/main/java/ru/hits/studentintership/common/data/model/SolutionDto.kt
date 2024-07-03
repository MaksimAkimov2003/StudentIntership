package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable
import ru.hits.studentintership.data.positions.model.UserDto

@Serializable
data class SolutionDto(
    val author: UserDto,
    val comment: String?,
    val files: List<File>,
    val id: String,
    val lastUpdateDateTime: String,
    val mark: String?,
    val state: String
)
package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable
import ru.hits.studentintership.data.positions.model.UserDto

@Serializable
data class File(
    val id: String,
    val name: String,
    val owner: UserDto,
    val size: Double,
    val uploadDateTime: String
)
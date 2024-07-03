package ru.hits.studentintership.data.positions.model

import kotlinx.serialization.Serializable
import ru.hits.studentintership.common.data.model.Company

@Serializable
data class PositionDto(
    val company: Company,
    val id: String,
    val positionStatus: String,
    var priority: Int,
    val programLanguage: ProgramLanguage,
    val speciality: Speciality,
    val user: UserDto
)
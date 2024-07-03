package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable
import ru.hits.studentintership.data.positions.model.ProgramLanguage
import ru.hits.studentintership.data.positions.model.Speciality

@Serializable
data class WishesDto(
    val comment: String?,
    val company: Company,
    val id: String,
    val internAmount: String,
    val programLanguage: ProgramLanguage?,
    val speciality: Speciality
)
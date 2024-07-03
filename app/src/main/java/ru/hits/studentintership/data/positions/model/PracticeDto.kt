package ru.hits.studentintership.data.positions.model

import kotlinx.serialization.Serializable
import ru.hits.studentintership.common.data.model.Company

@Serializable
data class PracticeDto(
    val id: String,
    val comment: String?,
    val company: Company,
)

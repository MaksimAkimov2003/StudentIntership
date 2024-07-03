package ru.hits.studentintership.data.positions.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatePositionBodyDto(
    val priority: Int,
    val positionStatus: String,
    val programLanguageId: String,
    val specialityId: String,
    val companyId: String,
)

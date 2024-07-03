package ru.hits.studentintership.data.meetings

import kotlinx.serialization.Serializable
import ru.hits.studentintership.common.data.model.Company

@Serializable
data class MeetingDto(
    val audience: String,
    val company: Company,
    val date: String,
    val dayOfWeek: String,
    val groups: List<Group>,
    val id: String,
    val pairNumber: String
)
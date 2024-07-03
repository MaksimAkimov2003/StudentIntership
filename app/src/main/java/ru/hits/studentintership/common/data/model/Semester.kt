package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Semester(
    val changeCompanyApplicationDeadline: String,
    val endDate: String,
    val id: String,
    val number: Int,
    val startDate: String,
    val studyYear: String
)
package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangePracticeApplicationCreateBody(
    val comment: String,
    val companyId: String,
    val notPartner: String?,
    val semesterId: String
)
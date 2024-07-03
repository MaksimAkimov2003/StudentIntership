package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SolutionCreateBody(
    val comment: String,
    val fileIds: List<String>
)

package ru.hits.studentintership.data.positions.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginationPositionsDto(
    val elements: List<PositionDto>,
    val pageNumber: Int,
    val pageSize: Int
)
package ru.hits.studentintership.presentation.positions.model

import ru.hits.studentintership.data.positions.model.PositionDto

data class PositionsState(
    val positions: List<PositionDto>
)
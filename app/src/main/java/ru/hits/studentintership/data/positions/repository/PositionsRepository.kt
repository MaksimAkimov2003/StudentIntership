package ru.hits.studentintership.data.positions.repository

import ru.hits.studentintership.data.positions.api.PositionsService
import ru.hits.studentintership.data.positions.model.CreatePositionBodyDto
import ru.hits.studentintership.data.positions.model.PositionDto
import javax.inject.Inject

class PositionsRepository @Inject constructor(
    private val positionsService: PositionsService,
) {
    suspend fun createPosition(createPositionBody: CreatePositionBodyDto): PositionDto =
        positionsService.createPosition(createPositionBodyDto = createPositionBody)
}
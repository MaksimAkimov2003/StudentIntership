package ru.hits.studentintership.data.positions.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.hits.studentintership.data.positions.model.CreatePositionBodyDto
import ru.hits.studentintership.data.positions.model.PositionDto


interface PositionsService {
    @GET("api/v1/positions/{userId}")
    suspend fun getPositions(
        @Path("userId") userId: String,
    ): List<PositionDto>

    @POST("api/v1/positions")
    suspend fun createPosition(
        @Body createPositionBodyDto: CreatePositionBodyDto,
    ): PositionDto

    @PUT("api/v1/positions/change-priority")
    suspend fun changePositionPriority(
        @Body positions: List<String>,
    )

    @PUT("/api/v1/positions/{positionId}/change-status")
    suspend fun changePositionStatus(
        @Path("positionId") positionId: String,
        @Body status: String,
    )

    @DELETE("/api/v1/positions/{positionId}")
    suspend fun deletePosition(
        @Path("positionId") positionId: String,
    ): Response<Unit>
}
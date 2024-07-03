package ru.hits.studentintership.data.meetings

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MeetingsService {
    @GET("/api/v1/meetings")
    suspend fun getMeetings(
        @Query("groupIds") groupIds: List<String>,
        @Query("from") dateFrom: String = "2024-07-01",
        @Query("to") dateTo: String = "2024-07-06",
    ): List<DayMeetingDto>
}
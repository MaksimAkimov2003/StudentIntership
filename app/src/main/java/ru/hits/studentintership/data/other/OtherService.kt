package ru.hits.studentintership.data.other

import okhttp3.Call
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import ru.hits.studentintership.common.data.model.ChangePracticeApplicationCreateBody
import ru.hits.studentintership.common.data.model.ChangePracticeApplicationDto
import ru.hits.studentintership.common.data.model.Company
import ru.hits.studentintership.common.data.model.LoginBodyDto
import ru.hits.studentintership.common.data.model.Semester
import ru.hits.studentintership.common.data.model.SolutionCreateBody
import ru.hits.studentintership.common.data.model.SolutionDto
import ru.hits.studentintership.common.data.model.TaskDto
import ru.hits.studentintership.common.data.model.TokenDto
import ru.hits.studentintership.common.data.model.WishesDto
import ru.hits.studentintership.data.positions.model.ProgramLanguage
import ru.hits.studentintership.data.positions.model.Speciality
import ru.hits.studentintership.data.positions.model.UserDto

interface OtherService {
    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body loginBody: LoginBodyDto,
    ): ResponseBody

    @GET("/api/v1/specialties")
    suspend fun getSpecialities(
    ): List<Speciality>

    @GET("api/v1/program-languages")
    suspend fun getProgramLanguages(
    ): List<ProgramLanguage>

    @GET("api/v1/companies")
    suspend fun getCompanies(
    ): List<Company>

    @GET("/api/v1/company-wishes/{companyId}")
    suspend fun getCompaniesWishes(
        @Path("companyId") companyId: String,
    ): List<WishesDto>

    @GET("/api/v1/users")
    suspend fun getUserInfo(
    ): UserDto

    @GET("/api/v1/change-practice-applications/student")
    suspend fun getChangePracticeApplications(
    ): List<ChangePracticeApplicationDto>

    @DELETE("/api/v1/change-practice-applications/{applicationId}")
    suspend fun deleteChangePracticeApplication(
        @Path("applicationId") applicationId: String,
    )

    @POST("/api/v1/change-practice-applications")
    suspend fun createChangePracticeApplication(
        @Body changePracticeApplicationCreateBody: ChangePracticeApplicationCreateBody,
    )

    @GET("/api/v1/semesters")
    suspend fun getSemesters(
    ): List<Semester>

    @GET("/api/v1/tasks/my")
    suspend fun getTasks(
    ): List<TaskDto>

    @GET("/api/v1/tasks/{id}")
    suspend fun getTask(
        @Path("id") taskId: String,
    ): TaskDto

    @GET("/api/v1/files/download/{id}")
    @Headers("Content-Type:application/octet-stream")
    @Streaming
    suspend fun downloadFile(
        @Path("id") fileId: String,
    ): Response<ResponseBody>

    @GET("/api/v1/tasks/{taskId}/solutions/my")
    suspend fun getSolutions(
        @Path("taskId") taskId: String,
    ): List<SolutionDto>

    @Multipart
    @POST("/api/v1/files/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    )

    @POST("/api/v1/tasks/{taskId}/solutions")
    suspend fun postSolution(
        @Path("taskId") taskId: String,
        @Body solutionCreateBody: SolutionCreateBody,
    )

    @PUT("/api/v1/tasks/{taskId}/solutions/{solutionId}")
    suspend fun changeSolution(
        @Path("taskId") taskId: String,
        @Path("solutionId") solutionId: String,
    )
}
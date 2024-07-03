package ru.hits.studentintership.presentation.login.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.hits.studentintership.common.data.model.TokenDto

interface UserService {
    @POST("api/v1/auth/login")
    suspend fun login(
        @Body
        email: String,
        @Body
        password: String
    ): Response<TokenDto>
}
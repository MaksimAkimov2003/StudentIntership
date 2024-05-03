package ru.hits.studentintership.login.data

import ru.hits.studentintership.common.domain.TokenDto
import ru.hits.studentintership.login.domain.LoginRepository

class LoginRepositoryImpl(
    private val loginApi: UserService
) : LoginRepository {
    override suspend fun login(email: String, password: String): TokenDto {
        val response = loginApi.login(email, password)
        if (response.isSuccessful) {
            return TokenDto(response.body()!!.access, response.body()!!.refresh)
        } else {
            throw Exception("${response.code()} ${response.message()}")
        }
    }
}
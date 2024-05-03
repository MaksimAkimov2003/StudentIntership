package ru.hits.studentintership.login.domain

import ru.hits.studentintership.common.domain.TokenDto

interface LoginRepository {
    suspend fun login(email: String, password: String): TokenDto
}

package ru.hits.studentintership.login.domain

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> = runCatching {
        loginRepository.login(
            email,
            password
        )
    }
}
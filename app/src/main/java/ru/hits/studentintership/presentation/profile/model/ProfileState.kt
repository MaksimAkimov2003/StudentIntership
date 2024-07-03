package ru.hits.studentintership.presentation.profile.model

import ru.hits.studentintership.common.data.model.ChangePracticeApplicationDto
import ru.hits.studentintership.data.positions.model.UserDto

data class ProfileState(
    val user: UserDto?,
    val changePracticeApplications: List<ChangePracticeApplicationDto>,
)

package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable
import ru.hits.studentintership.data.positions.model.UserDto

@Serializable
data class ChangePracticeApplicationDto(
    val author: UserDto,
    val checkingEmployee: UserDto?,
    val comment: String,
    val creationDate: String,
    val id: String,
    val lastUpdatedDate: String,
    val notPartner: String?,
    val partner: Company?,
    val semester: Semester,
    val status: String
)
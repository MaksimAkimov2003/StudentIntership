package ru.hits.studentintership.data.positions.model

import kotlinx.serialization.Serializable

@Serializable
data class Speciality(
    val id: String,
    val name: String
)
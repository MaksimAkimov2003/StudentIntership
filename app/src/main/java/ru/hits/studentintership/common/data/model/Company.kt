package ru.hits.studentintership.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val id: String,
    val isVisible: Boolean,
    val name: String,
    val websiteLink: String?
)
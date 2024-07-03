package ru.hits.studentintership.presentation.companies.model

import ru.hits.studentintership.common.data.model.Company

data class CompanyWithWishes(
    val company: Company,
    val wishes: List<String>?,
)

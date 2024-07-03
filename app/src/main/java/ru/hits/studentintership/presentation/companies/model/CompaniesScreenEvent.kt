package ru.hits.studentintership.presentation.companies.model

import ru.hits.studentintership.core.Event

sealed class CompaniesScreenEvent : Event {
    data class ShowSnackbar(val message: String) : CompaniesScreenEvent()
}
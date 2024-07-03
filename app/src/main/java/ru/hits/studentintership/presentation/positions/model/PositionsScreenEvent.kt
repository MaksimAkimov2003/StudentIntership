package ru.hits.studentintership.presentation.positions.model

import ru.hits.studentintership.core.Event

sealed class PositionsScreenEvent : Event {
    data class ShowSnackbar(val message: String) : PositionsScreenEvent()
}
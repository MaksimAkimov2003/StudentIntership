package ru.hits.studentintership.presentation.positions.creation.editing.model

import ru.hits.studentintership.core.Event

sealed class PositionCreatingEditingScreenEvent : Event {
    data class ShowSnackbar(val message: String) : PositionCreatingEditingScreenEvent()
}
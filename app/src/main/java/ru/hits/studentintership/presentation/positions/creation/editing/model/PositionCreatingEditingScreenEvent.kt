package ru.hits.studentintership.presentation.positions.creation.editing.model

sealed interface PositionCreatingEditingScreenEvent {
    data object NavigateToPositionsScreen : PositionCreatingEditingScreenEvent
}
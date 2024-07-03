package ru.hits.studentintership.presentation.tasks.model

import ru.hits.studentintership.core.Event

sealed class TasksScreenEvent: Event {
    data class ShowSnackbar(val message: String) : TasksScreenEvent()
}

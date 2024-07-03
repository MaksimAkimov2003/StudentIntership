package ru.hits.studentintership.presentation.meetings.model

import ru.hits.studentintership.core.Event

sealed class MeetingsScreenEvent : Event {
    data class ShowSnackbar(val message: String) : MeetingsScreenEvent()
}

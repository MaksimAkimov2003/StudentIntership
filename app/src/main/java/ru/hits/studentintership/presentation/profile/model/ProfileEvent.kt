package ru.hits.studentintership.presentation.profile.model

import ru.hits.studentintership.core.Event

sealed class ProfileEvent: Event {
    data class ShowSnackbar(val message: String) : ProfileEvent()
}
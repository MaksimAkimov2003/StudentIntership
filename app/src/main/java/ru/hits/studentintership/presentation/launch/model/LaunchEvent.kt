package ru.hits.studentintership.presentation.launch.model

import ru.hits.studentintership.core.Event

sealed class LaunchEvent : Event {
    data object OnAuthorized : LaunchEvent()
    data object OnUnauthorized : LaunchEvent()
}

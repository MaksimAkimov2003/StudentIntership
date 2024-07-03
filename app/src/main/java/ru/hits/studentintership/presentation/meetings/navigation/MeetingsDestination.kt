package ru.hits.studentintership.presentation.meetings.navigation

import ru.hits.studentintership.common.presentation.Destination

object MeetingsDestination : Destination() {
    val groupId = "group_id"

    override fun args(): List<String> =
        listOf(groupId)
}
package ru.hits.studentintership.presentation.positions.navigation

import ru.hits.studentintership.common.presentation.Destination

object PositionsDestination : Destination() {
    val userId = "user_id"

    override fun args(): List<String> =
        listOf(userId)
}
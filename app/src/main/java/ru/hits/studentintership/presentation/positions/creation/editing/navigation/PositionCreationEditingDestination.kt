package ru.hits.studentintership.presentation.positions.creation.editing.navigation

import ru.hits.studentintership.common.presentation.Destination

object PositionCreationEditingDestination : Destination() {
    const val positionIdArg = "position_id"
    const val positionsSizeArg = "positions_size"
    val userId = "user_id"

    override fun args(): List<String> =
        listOf(positionIdArg, positionsSizeArg, userId)
}
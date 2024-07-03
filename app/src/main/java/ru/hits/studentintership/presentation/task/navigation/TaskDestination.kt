package ru.hits.studentintership.presentation.task.navigation

import ru.hits.studentintership.common.presentation.Destination

object TaskDestination: Destination() {
    val taskIdArg = "task_id"

    override fun args(): List<String> =
        listOf(taskIdArg)
}
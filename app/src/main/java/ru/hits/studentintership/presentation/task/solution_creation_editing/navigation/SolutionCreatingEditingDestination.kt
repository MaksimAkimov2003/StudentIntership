package ru.hits.studentintership.presentation.task.solution_creation_editing.navigation

import ru.hits.studentintership.common.presentation.Destination

object SolutionCreatingEditingDestination: Destination() {
    val solutionId = "solution_id"
    val taskId = "task_id"
    val uri = "uri"

    override fun args(): List<String> =
        listOf(solutionId, taskId, uri)
}
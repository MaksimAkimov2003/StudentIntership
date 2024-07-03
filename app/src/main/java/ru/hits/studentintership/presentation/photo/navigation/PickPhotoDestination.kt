package ru.hits.studentintership.presentation.photo.navigation

import ru.hits.studentintership.common.presentation.Destination
import ru.hits.studentintership.presentation.task.solution_creation_editing.navigation.SolutionCreatingEditingDestination

object PickPhotoDestination : Destination() {
    val solutionId = "solution_id"
    val taskId = "task_id"
    override fun args(): List<String> =
        listOf(SolutionCreatingEditingDestination.solutionId, SolutionCreatingEditingDestination.taskId)
}

package ru.hits.studentintership.presentation.task.solution_creation_editing.model

import ru.hits.studentintership.common.data.model.File
import ru.hits.studentintership.common.data.model.SolutionDto

data class SolutionCreatingEditingState(
    val solution: SolutionDto?,
    val comment: String,
    val files: List<File>,
    val uri: String,
)

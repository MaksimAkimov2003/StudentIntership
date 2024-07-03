package ru.hits.studentintership.presentation.task.model

import ru.hits.studentintership.common.data.model.File
import ru.hits.studentintership.common.data.model.SolutionDto
import ru.hits.studentintership.common.data.model.TaskDto

data class TaskState(
    val task: TaskDto?,
    val solutions: List<SolutionDto>,
    )

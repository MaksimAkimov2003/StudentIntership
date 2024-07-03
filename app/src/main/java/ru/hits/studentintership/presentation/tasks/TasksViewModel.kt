package ru.hits.studentintership.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hits.studentintership.core.Event
import ru.hits.studentintership.core.EventQueue
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.presentation.tasks.model.TasksScreenEvent
import ru.hits.studentintership.presentation.tasks.model.TasksState
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val otherService: OtherService,
) : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    val screenEvents: EventQueue = EventQueue()

    init {
        getTasks()
    }

    fun offerEvent(event: Event) {
        screenEvents.offerEvent(event)
    }

    private fun createInitialState() = TasksState(
        tasks = emptyList(),
    )

    fun getTasks() = viewModelScope.launch {
        try {
            _state.update {
                it.copy(
                    tasks = otherService.getTasks()
                )
            }
        } catch(e: Exception) {
            offerEvent(TasksScreenEvent.ShowSnackbar(e.localizedMessage ?: "Ошибка сервера"))
        }
    }

}
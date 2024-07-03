package ru.hits.studentintership.presentation.positions

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hits.studentintership.core.Event
import ru.hits.studentintership.core.EventQueue
import ru.hits.studentintership.data.positions.api.PositionsService
import ru.hits.studentintership.data.positions.model.PositionDto
import ru.hits.studentintership.data.positions.repository.PositionsRepository
import ru.hits.studentintership.presentation.positions.model.PositionsScreenEvent
import ru.hits.studentintership.presentation.positions.model.PositionsState
import ru.hits.studentintership.presentation.positions.navigation.PositionsDestination
import javax.inject.Inject

@HiltViewModel
class PositionsViewModel @Inject constructor(
    private val positionsService: PositionsService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val userId: String = requireNotNull(savedStateHandle[PositionsDestination.userId])

    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private val currentScreenState: PositionsState get() = _state.value

    val screenEvents: EventQueue = EventQueue()

    fun offerEvent(event: Event) {
        screenEvents.offerEvent(event)
    }

    init {
        getPositions()
    }

    fun getPositions() = viewModelScope.launch {
        try {
            val studentPositions = positionsService.getPositions(userId)
            _state.update { state ->
                state.copy(positions = studentPositions.sortedByDescending { it.priority }.toMutableList())
            }
        } catch(e: Exception) {
            offerEvent(PositionsScreenEvent.ShowSnackbar(e.localizedMessage ?: "Ошибка сервера"))
        }
    }

    private fun createInitialState() =
        PositionsState(
            positions = mutableListOf(),
        )

    fun swapPositions(index1: Int, index2: Int) = viewModelScope.launch {
        val newList = currentScreenState.positions.toMutableList()
        newList.apply {
            add(index1, removeAt(index2))
        }
        _state.update {
            it.copy(positions = newList)
        }
    }

    fun deletePosition(positionId: String) = viewModelScope.launch {
        try {
            positionsService.deletePosition(positionId = positionId)
        } catch(e: Exception) {
            offerEvent(PositionsScreenEvent.ShowSnackbar(e.localizedMessage ?: "Ошибка сервера"))
        }
        getPositions()
    }

    fun savePositionPriority() = viewModelScope.launch {
        try {
            positionsService.changePositionPriority(positions = currentScreenState.positions.reversed().map { it.id })
        } catch(e: Exception) {
            offerEvent(PositionsScreenEvent.ShowSnackbar(e.localizedMessage ?: "Ошибка сервера"))
        }
    }
}

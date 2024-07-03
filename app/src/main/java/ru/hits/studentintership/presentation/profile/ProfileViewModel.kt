package ru.hits.studentintership.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hits.studentintership.common.data.shared.TokenManager
import ru.hits.studentintership.core.Event
import ru.hits.studentintership.core.EventQueue
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.presentation.profile.model.ProfileEvent
import ru.hits.studentintership.presentation.profile.model.ProfileState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val otherService: OtherService,
    private val tokenManager: TokenManager,
) : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    val screenEvents: EventQueue = EventQueue()

    init {
        getUserInfo()
        getChangePracticeApplications()
    }


    fun offerEvent(event: Event) {
        screenEvents.offerEvent(event)
    }

    private fun createInitialState() = ProfileState(
        user = null,
        changePracticeApplications = emptyList(),
    )

    private fun getUserInfo() = viewModelScope.launch {
        try {
            val user = otherService.getUserInfo()
            _state.update {
                it.copy(user = user)
            }
        } catch(e: Exception) {
            offerEvent(ProfileEvent.ShowSnackbar(e.localizedMessage ?: "Ошибка сервера"))
        }
    }

    private fun getChangePracticeApplications() = viewModelScope.launch {
        try {
            _state.update {
                it.copy(changePracticeApplications = otherService.getChangePracticeApplications())
            }
        } catch(e: Exception) {
            offerEvent(ProfileEvent.ShowSnackbar(e.localizedMessage ?: "Ошибка сервера"))
        }
    }

    fun deleteChangePracticeApplication(applicationId: String) = viewModelScope.launch {
        try {
            otherService.deleteChangePracticeApplication(applicationId = applicationId)
            getChangePracticeApplications()
        } catch(e: Exception) {
            offerEvent(ProfileEvent.ShowSnackbar(e.localizedMessage ?: "Ошибка сервера"))
        }
    }

    fun logout() = viewModelScope.launch {
        tokenManager.saveToken(null)
    }
}
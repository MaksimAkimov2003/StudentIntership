package ru.hits.studentintership.presentation.launch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.studentintership.common.data.shared.TokenManager
import ru.hits.studentintership.core.Event
import ru.hits.studentintership.core.EventQueue
import ru.hits.studentintership.presentation.launch.model.LaunchEvent
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val tokenManager: TokenManager,
): ViewModel() {
    val screenEvents: EventQueue = EventQueue()
    init {
        auth()
    }

    fun auth() = viewModelScope.launch {
        val token = tokenManager.getToken()
        Log.d("token", token ?: "null")
        if (token != null)
            offerEvent(LaunchEvent.OnAuthorized)
        else
            offerEvent(LaunchEvent.OnUnauthorized)
    }



    fun offerEvent(event: Event) {
        screenEvents.offerEvent(event)
    }
}
package ru.hits.studentintership.presentation.photo

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hits.studentintership.common.data.shared.TokenManager
import ru.hits.studentintership.presentation.photo.navigation.PickPhotoDestination
import javax.inject.Inject

@HiltViewModel
class PickPhotoViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    val solutionId: String? = savedStateHandle[PickPhotoDestination.solutionId]
    val taskId: String = requireNotNull(savedStateHandle[PickPhotoDestination.taskId])

    fun onPhotoClick(uri: Uri) {
        tokenManager.saveUri(uri.toString())
    }
}
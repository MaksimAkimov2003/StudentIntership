package ru.hits.studentintership.presentation.task.solution_creation_editing

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.hits.studentintership.common.data.model.SolutionCreateBody
import ru.hits.studentintership.common.data.shared.TokenManager
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.presentation.task.solution_creation_editing.model.SolutionCreatingEditingState
import ru.hits.studentintership.presentation.task.solution_creation_editing.navigation.SolutionCreatingEditingDestination
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class SolutionCreatingEditingViewModel @Inject constructor(
    private val application: Application,
    private val otherService: OtherService,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    val solutionId: String? = savedStateHandle[SolutionCreatingEditingDestination.solutionId]
    val taskId: String = requireNotNull(savedStateHandle[SolutionCreatingEditingDestination.taskId])
    var uri: String? = tokenManager.getUri()

    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    init {
        if (solutionId != null)
            getSolution()
        uri = tokenManager.getUri()
        uri?.let { uploadFile(it) }
        observeImageUriOfPickedPhoto(savedStateHandle)
    }

    private fun createInitialState() = SolutionCreatingEditingState(
        solution = null,
        comment = "",
        files = emptyList(),
        uri = ""
    )

    fun onCommentChange(comment: String) {
        _state.update {
            it.copy(
                comment = comment
            )
        }
    }

    fun getSolution() = viewModelScope.launch {
        val allSolutions = otherService.getSolutions(taskId = taskId)
        val solution = allSolutions.find { it.id == solutionId }!!
        _state.update {
            it.copy(
                solution = solution,
                comment = solution.comment ?: "",
                files = solution.files,
            )
        }
    }

    fun uploadFile(uri: String) = viewModelScope.launch {
        val avatarUri = uri.toUri()
        val file = avatarUri.toFile()

        otherService.uploadFile(
            file = MultipartBody.Part.createFormData(
                name = "avatar",
                filename = file.name,
                body = file.asRequestBody(contentType = "image/*".toMediaType())
            )
        )
    }

    fun observeImageUriOfPickedPhoto(savedStateHandle: SavedStateHandle) =
        viewModelScope.launch {
            savedStateHandle.getStateFlow(key = "PICK_PHOTO_URI_KEY", initialValue = "").collect { uri ->
                if (uri != "") {
                    _state.update {
                        it.copy(
                            uri = uri
                        )
                    }
                    uploadFile(uri)
                }
            }
        }

    fun downloadFile(fileId: String) = viewModelScope.launch {
        val res = otherService.downloadFile(fileId = fileId)
        val head = res.headers()["Content-Disposition"]

        val inputStream = res.body()?.byteStream()
        inputStream?.let {
            if (head != null) {
                val name = head.substringAfter('_').substringBefore('?')
                Log.d("name", name)

                if (head.endsWith(".docx")) {
                    val file = File(application.getExternalFilesDir(null), name)
                    FileOutputStream(file).use { output ->
                        output.write(inputStream.readBytes())
                    }

                    val intent = Intent(Intent.ACTION_VIEW)

                    intent.setDataAndType(FileProvider.getUriForFile(application, "ru.hits.studentintership.provider", file), "application/msword")
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    application.startActivity(intent)
                } else if (head.endsWith(".png")) {
                    val file = File(application.getExternalFilesDir(null), name)
                    FileOutputStream(file).use { output ->
                        output.write(inputStream.readBytes())
                    }

                    val intent = Intent(Intent.ACTION_VIEW)

                    intent.setDataAndType(FileProvider.getUriForFile(application, "ru.hits.studentintership.provider", file), "image/jpeg")
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    application.startActivity(intent)
                }
            }

        }
    }

    fun changeSolution() = viewModelScope.launch {
        if (solutionId != null) {
            otherService.changeSolution(taskId = taskId, solutionId = solutionId)
        }
    }

    fun postSolution() = viewModelScope.launch {
        otherService.postSolution(taskId = taskId, solutionCreateBody = SolutionCreateBody(
            comment = _state.value.comment,
            fileIds = _state.value.files.map { it.id }
        ))
    }
}
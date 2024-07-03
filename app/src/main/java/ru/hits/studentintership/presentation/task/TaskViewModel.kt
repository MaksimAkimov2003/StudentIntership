package ru.hits.studentintership.presentation.task

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
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.presentation.task.model.TaskState
import ru.hits.studentintership.presentation.task.navigation.TaskDestination
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val application: Application,
    private val otherService: OtherService,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    private val taskId: String = requireNotNull(savedStateHandle[TaskDestination.taskIdArg])

    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    init {
        getTask()
        getSolutions()
    }

    private fun createInitialState() = TaskState(
        task = null,
        solutions = emptyList(),
    )


    fun getTask() = viewModelScope.launch {
        _state.update {
            it.copy(
                task = otherService.getTask(taskId = taskId)
            )
        }
    }

    fun getSolutions() = viewModelScope.launch {
        _state.update {
            it.copy(
                solutions = otherService.getSolutions(taskId = taskId)
            )
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

                if (head.endsWith(".docx") || head.endsWith(".doc")) {
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
}
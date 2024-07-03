package ru.hits.studentintership.presentation.task.solution_creation_editing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.studentintership.presentation.task.convertDateFormat

@Composable
fun SolutionCreatingEditingScreen(
    navigateToTasksScreen: () -> Unit,
    navigateToPickPhotoScreen: (solutionId: String?, taskId: String) -> Unit,
    viewModel: SolutionCreatingEditingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.files) {
        viewModel.getSolution()
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(24.dp),
    ) {
        TextField(
            value = state.comment, onValueChange = { viewModel.onCommentChange(it) },
            label = {
                Text(text = "Комментарий")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Файлы")
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navigateToPickPhotoScreen(viewModel.solutionId, viewModel.taskId) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(state.files) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.downloadFile(it.id) }
                ) {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = null)
                    Column {
                        Text(text = it.name)
                        Text(text = "Добавлено " + convertDateFormat(it.uploadDateTime))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = {
                viewModel.changeSolution()
                navigateToTasksScreen()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Сохранить")
        }

    }
}
package ru.hits.studentintership.presentation.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navigateToSolutionCreationEditing: (taskId: String) -> Unit,
    navigateToSolutionScreen: (solutionId: String, taskId: String) -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val task = state.task

    LaunchedEffect(key1 = state.solutions) {
        viewModel.getSolutions()
    }

    task?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = task.name,
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(20.dp))
            val creationDate = convertDateFormat(task.createdAt)
            Text(
                text = task.author.fullName + " • " + "Опубликовано $creationDate",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Срок сдачи: ${convertDateFormatWithTime(task.deadline)}",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            task.description?.let {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Text(
                text = "Вложения",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            task.files.forEach {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.downloadFile(it.id) }) {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = null)
                    Column {
                        Text(text = it.name)
                        Text(text = "Добавлено " + convertDateFormat(it.uploadDateTime))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Мои решения",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            state.solutions.forEach { solution ->
                Card(
                    onClick = { navigateToSolutionScreen(solution.id, task.id) },
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                solution.comment?.let { it1 -> Text(text = it1) }
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider()
                                Spacer(modifier = Modifier.height(8.dp))
                                Row {
                                    Text(
                                        text = if (solution.state == "ACCEPTED") "Принято"
                                        else if (solution.state == "REJECTED") "Отклонено"
                                        else "Новое"
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                    if (solution.mark?.isNotBlank() == true)
                                        Text(text = "Оценка: " + solution.mark)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        solution.files.forEach {
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
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { navigateToSolutionCreationEditing(task.id) }, modifier = Modifier.fillMaxWidth()) {
                Text("Приложить ответ к заданию")
            }
        }
    }
}

fun convertDateFormat(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    val formattedDate = outputDateFormat.format(date)

    return formattedDate
}

fun convertDateFormatWithTime(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    val formattedDate = outputDateFormat.format(date)

    return formattedDate
}
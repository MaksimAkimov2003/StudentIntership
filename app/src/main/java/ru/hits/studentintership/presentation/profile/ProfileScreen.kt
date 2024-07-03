package ru.hits.studentintership.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.hits.studentintership.R
import ru.hits.studentintership.common.data.model.ChangePracticeApplicationDto
import ru.hits.studentintership.presentation.common.ErrorSnackbar
import ru.hits.studentintership.presentation.profile.model.ProfileEvent

@Composable
fun ProfileScreen(
    navigateToCompanies: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToTasks: () -> Unit,
    navigateToMyPositions: (userId: String) -> Unit,
    navigateToMeetings: (group: String) -> Unit,
    navigateToChangePracticeApplicationCreation: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val snackbarState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    val showSnackbar: (message: String) -> Unit = { message ->
        snackbarScope.launch {
            snackbarState.showSnackbar(message)
        }
    }

    viewModel.screenEvents.CollectEvent { event ->
        when (event) {
            is ProfileEvent.ShowSnackbar -> showSnackbar(
                event.message
            )
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Профиль",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.ic_default_avatar),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            state.user?.let { user ->
                Text(
                    text = "Имя",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = user.fullName,
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )

                user.group?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Группа",
                        fontSize = 25.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = user.group.name,
                        fontSize = 22.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Email",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = user.email,
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )

                if (user.currentPractice != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Текущее место практики",
                        fontSize = 25.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = user.currentPractice.company.name,
                        fontSize = 22.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                    // Spacer(modifier = Modifier.height(16.dp))
                    // if (state.changePracticeApplications.isNotEmpty()) {
                    //     Text(
                    //         text = "Мои заявки на изменение места практики",
                    //         fontSize = 25.sp,
                    //         color = Color.Black,
                    //         fontWeight = FontWeight.ExtraBold,
                    //     )
                    //     Spacer(modifier = Modifier.height(16.dp))
                    //     LazyColumn {
                    //         items(state.changePracticeApplications) { application ->
                    //             ChangePracticeApplication(application) { viewModel.deleteChangePracticeApplication(it) }
                    //         }
                    //     }
                    // }
                } else {
                    TextButton(onClick = { navigateToMyPositions(user.id) }) {
                        Text(text = "Мои позиции")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // if (state.user?.currentPractice != null) {
            //     TextButton(onClick = navigateToChangePracticeApplicationCreation, modifier = Modifier.fillMaxWidth()) {
            //         Text(text = "Создать заявку на смену места практики")
            //     }
            // }
            // TextButton(onClick = navigateToTasks, modifier = Modifier.fillMaxWidth()) {
            //     Text(text = "Мои задания")
            // }
            val group = state.user?.group
            val userId = state.user?.id
            if (group != null) {
                TextButton(onClick = { navigateToMeetings(group.id) }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Календарь встреч")
                }
            }

            if (userId != null) {
                TextButton(onClick = {
                    navigateToMyPositions(userId)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Мои позиции")
                }
            }
            TextButton(onClick = {
                navigateToCompanies()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Компании")
            }
            TextButton(onClick = {
                viewModel.logout()
                navigateToLogin()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Выйти")
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            ErrorSnackbar(
                snackbarHostState = snackbarState
            )
        }
    }
}

@Composable
fun ChangePracticeApplication(application: ChangePracticeApplicationDto, deleteChangePracticeApplication: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .weight(1.5f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row {
                    Text(
                        text = "Дата создания",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = application.creationDate.substringBefore('T'),
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Row {
                    Text(
                        text = "Статус заявки",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (application.status == "REJECTED") "Отклонено"
                        else if (application.status == "CONSIDERATION") "В работе"
                        else if (application.status == "QUEUE") "Открыта"
                        else "Принято",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Row {
                    Text(
                        text = "Компания",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = application.notPartner ?: application.partner?.name ?: "",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        text = "Семестр",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = application.semester.number.toString() + " (" + application.semester.startDate + " - " + application.semester.endDate + ")",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        maxLines = 3,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        if (application.status == "QUEUE") {
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { deleteChangePracticeApplication(application.id) }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
            }
        }
    }
}
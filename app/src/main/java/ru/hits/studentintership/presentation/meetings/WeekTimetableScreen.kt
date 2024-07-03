package ru.hits.studentintership.presentation.meetings

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.hits.studentintership.data.meetings.MeetingDto
import ru.hits.studentintership.presentation.common.ErrorSnackbar
import ru.hits.studentintership.presentation.meetings.model.DayEntity
import ru.hits.studentintership.presentation.meetings.model.MeetingsScreenEvent
import ru.hits.studentintership.presentation.meetings.model.MeetingsState
import ru.hits.studentintership.presentation.meetings.ui.ScrollTable
import ru.hits.studentintership.presentation.meetings.ui.TimetableAdapter
import ru.hits.studentintership.presentation.positions.model.PositionsScreenEvent

@Composable
fun MeetingsScreen(
    context: Context = LocalContext.current,
    navController: NavController,
    viewModel: MeetingsViewModel = hiltViewModel(),
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
            is MeetingsScreenEvent.ShowSnackbar -> showSnackbar(
                event.message
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp, 16.dp, 16.dp, 32.dp)) {
            RenderContentState(state, context, navController)
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
private fun RenderContentState(
    state: MeetingsState,
    context: Context,
    navController: NavController
) {
    Column {
        DrawTimetableHeader(
            title = "Встречи",
            date = state.currentDate,
            currentWeek = state.currentWeek, state.currentDay
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Transparent, MaterialTheme.shapes.large),
            contentAlignment = Alignment.TopCenter
        ) {
            // if (!state.isListTimetable) {
            //     ScrollTable(TimetableAdapter(state.timetable.days, context))
            // } else {
                if (state.timetable.days != emptyList<DayEntity>())
                    ScrollTable(TimetableAdapter(state.timetable.days, context))
                    // DrawListTimetable(state)
            // }
        }
        // DrawBottomBar(viewModel, navController)
    }
}

private data class TimeSlot(val start: String, val end: String)

@Composable
private fun DrawListTimetable(state: MeetingsState) {
    val dayMillis = 1000 * 60 * 60 * 24
    println(((state.currentDay - state.currentWeek.startDate) / dayMillis).toInt())
    Log.d("timetable", state.timetable.days.toString())
    val currentDay = state.timetable.days[((state.currentDay - state.currentWeek.startDate) / dayMillis).toInt()-1].timeSlots
    val timeslots = listOf(
        TimeSlot("8:45", "10:20"),
        TimeSlot("10:35", "12:10"),
        TimeSlot("12:25", "14:00"),
        TimeSlot("14:45", "16:20"),
        TimeSlot("16:35", "18:10"),
        TimeSlot("18:25", "20:00"),
        TimeSlot("20:15", "21:50")
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        var isLastPair = false
        var lastPairIndex = 0
        Box(modifier = Modifier.height(16.dp))
        timeslots.forEachIndexed { i, it ->
            currentDay.forEach { slot ->
                if (i + 1 == slot.slotNumber) {
                    if (isLastPair && slot.pairs != null && lastPairIndex != i - 1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(
                                    Color.Green,
                                    MaterialTheme.shapes.medium
                                )
                        ) {
                            Text(
                                "${timeslots[lastPairIndex + 1].start} - ${timeslots[i - 1].end}",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                    slot.pairs?.forEach { pair ->
                        DrawPairCard(pair = pair, it, i + 1)
                        isLastPair = true
                        lastPairIndex = i
                    }
                }
            }
        }
        Box(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DrawPairCard(pair: MeetingDto, timeslot: TimeSlot, timeslotNumber: Int) {
    val color = Color.Blue
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .background(
                Color.Magenta,
                MaterialTheme.shapes.medium
            )
            .offset(0.dp, (-8).dp)
    ) {
        Text(text = pair.company.name, modifier = Modifier.padding(horizontal = 8.dp))
        Row(modifier = Modifier.padding(vertical = 2.dp)) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Text(text = pair.audience)
        }
        Row(modifier = Modifier.padding(vertical = 2.dp)) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Text(text = pair.groups[0].name)
        }
        Row(modifier = Modifier.padding(vertical = 2.dp)) {
            Text(text = "${timeslot.start} - ${timeslot.end}", modifier = Modifier.padding(horizontal = 8.dp))
            Text(
                text = "$timeslotNumber-ая пара", modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(), textAlign = TextAlign.End
            )
        }
    }
}

private fun formatCurrentWeek(week: WeekDateEntity) =
    "${DateFormat.format("MMMM dd", week.startDate)} - ${DateFormat.format("MMMM dd", week.endDate)}"

data class WeekDateEntity(
    val startDate: Long,
    val endDate: Long
)

@Composable
private fun ColumnScope.DrawTimetableHeader(
    title: String,
    date: String,
    currentWeek: WeekDateEntity,
    // viewModel: MeetingsViewModel,
    currentDay: Long
) {
    Box(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp)
        ) {
            IconButton(onClick = { /*viewModel.loadLastWeek(timetableType, isListTimetable)*/ }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                )
            }
            // if (!isListTimetable) {
            //     Text(
            //         text = formatCurrentWeek(currentWeek),
            //         style = MaterialTheme.typography.bodyMedium,
            //         modifier = Modifier
            //             .weight(1f)
            //             .align(Alignment.CenterVertically),
            //         textAlign = TextAlign.Center
            //     )
            // } else {
                val dayMillis = 1000 * 60 * 60 * 24
                for (i in 0..(currentWeek.endDate - currentWeek.startDate) / dayMillis) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { /*viewModel.setCurrentDay(currentWeek.startDate + i * dayMillis)*/ }) {
                        Text(
                            text = DateFormat.format("EE", currentWeek.startDate + i * dayMillis).toString(),
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Box(
                            modifier = Modifier.background(
                                Color.Transparent,
                                RoundedCornerShape(100)
                            ).aspectRatio(1f), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = DateFormat.format("dd", currentWeek.startDate + i * dayMillis).toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            // }
            IconButton(onClick = { /*viewModel.loadNextWeek(timetableType, isListTimetable)*/ }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "",
                )
            }
        }
    }
}

@Composable
private fun DrawBottomBar(viewModel: MeetingsViewModel, navController: NavController) {
    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Blue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp)
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                IconButton(onClick = { /*viewModel.changeTimetable()*/ }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "",
                    )
                }
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                IconButton(onClick = { /*viewModel.setToday()*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "",
                    )
                }
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                IconButton(onClick = { /*navController.navigate(Screen.MainMenuScreen.route)*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "",
                    )
                }
            }
        }
    }
}




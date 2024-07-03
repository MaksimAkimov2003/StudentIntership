package ru.hits.studentintership.presentation.meetings

import android.text.format.DateFormat
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
import ru.hits.studentintership.data.meetings.MeetingDto
import ru.hits.studentintership.data.meetings.MeetingsService
import ru.hits.studentintership.presentation.meetings.model.DayEntity
import ru.hits.studentintership.presentation.meetings.model.MeetingsScreenEvent
import ru.hits.studentintership.presentation.meetings.model.MeetingsState
import ru.hits.studentintership.presentation.meetings.model.TimeSlotEntity
import ru.hits.studentintership.presentation.meetings.model.WeekEntity
import ru.hits.studentintership.presentation.meetings.navigation.MeetingsDestination
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val meetingsService: MeetingsService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val groupId: String? = savedStateHandle[MeetingsDestination.groupId]

    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    val screenEvents: EventQueue = EventQueue()

    fun offerEvent(event: Event) {
        screenEvents.offerEvent(event)
    }

    init {
        if (groupId != null)
            getMeetings(groupId)
    }

    private fun createInitialState() = MeetingsState(
        meetings = emptyList(),
        mondayPairs = emptyList(),
        tuesdayPairs = emptyList(),
        wednesdayPairs = emptyList(),
        thursdayPairs = emptyList(),
        fridayPairs = emptyList(),
        saturdayPairs = emptyList(),
        timetable = WeekEntity(emptyList()),
        currentDay = getCurrentDay(),
        currentDate = getCurrentDateByWeek(getCurrentWeek()),
        currentWeek = getCurrentWeek()
    )

    fun getMeetings(groupId: String) = viewModelScope.launch {

        try {
            val meetings = meetingsService.getMeetings(groupIds = listOf(groupId))

            val mondayPairs: MutableList<MeetingDto> = mutableListOf()
            meetings.forEach {
                mondayPairs.addAll(it.mondayMeetings)
            }

            val tuesdayPairs: MutableList<MeetingDto> = mutableListOf()
            meetings.forEach {
                tuesdayPairs.addAll(it.tuesdayMeetings)
            }

            val wednesdayPairs: MutableList<MeetingDto> = mutableListOf()
            meetings.forEach {
                wednesdayPairs.addAll(it.wednesdayMeetings)
            }

            val thursdayPairs: MutableList<MeetingDto> = mutableListOf()
            meetings.forEach {
                thursdayPairs.addAll(it.thursdayMeetings)
            }

            val fridayPairs: MutableList<MeetingDto> = mutableListOf()
            meetings.forEach {
                fridayPairs.addAll(it.fridayMeetings)
            }

            val saturdayPairs: MutableList<MeetingDto> = mutableListOf()
            meetings.forEach {
                saturdayPairs.addAll(it.saturdayMeetings)
            }

            // slotNumber begins with 1

            val mondaySlots = DayEntity(
                weekDay = "Monday",
                day = "",
                countClasses = 0,
                timeSlots = listOf(
                    TimeSlotEntity(
                        slotNumber = 1,
                        pairs = mondayPairs.filter {
                            it.pairNumber == "FIRST"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 2,
                        pairs = mondayPairs.filter {
                            it.pairNumber == "SECOND"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 3,
                        pairs = mondayPairs.filter {
                            it.pairNumber == "THIRD"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 4,
                        pairs = mondayPairs.filter {
                            it.pairNumber == "FOURTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 5,
                        pairs = mondayPairs.filter {
                            it.pairNumber == "FIFTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 6,
                        pairs = mondayPairs.filter {
                            it.pairNumber == "SIXTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 7,
                        pairs = mondayPairs.filter {
                            it.pairNumber == "SEVENTH"
                        }
                    ),
                )
            )

            val tuesdaySlots = DayEntity(
                weekDay = "Tuesday",
                day = "",
                countClasses = 0,
                timeSlots = listOf(
                    TimeSlotEntity(
                        slotNumber = 1,
                        pairs = tuesdayPairs.filter {
                            it.pairNumber == "FIRST"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 2,
                        pairs = tuesdayPairs.filter {
                            it.pairNumber == "SECOND"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 3,
                        pairs = tuesdayPairs.filter {
                            it.pairNumber == "THIRD"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 4,
                        pairs = tuesdayPairs.filter {
                            it.pairNumber == "FOURTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 5,
                        pairs = tuesdayPairs.filter {
                            it.pairNumber == "FIFTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 6,
                        pairs = tuesdayPairs.filter {
                            it.pairNumber == "SIXTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 7,
                        pairs = tuesdayPairs.filter {
                            it.pairNumber == "SEVENTH"
                        }
                    ),
                )
            )

            val wednesdaySlots = DayEntity(
                weekDay = "Wednesday",
                day = "",
                countClasses = 0,
                timeSlots = listOf(
                    TimeSlotEntity(
                        slotNumber = 1,
                        pairs = wednesdayPairs.filter {
                            it.pairNumber == "FIRST"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 2,
                        pairs = wednesdayPairs.filter {
                            it.pairNumber == "SECOND"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 3,
                        pairs = wednesdayPairs.filter {
                            it.pairNumber == "THIRD"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 4,
                        pairs = wednesdayPairs.filter {
                            it.pairNumber == "FOURTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 5,
                        pairs = wednesdayPairs.filter {
                            it.pairNumber == "FIFTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 6,
                        pairs = wednesdayPairs.filter {
                            it.pairNumber == "SIXTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 7,
                        pairs = wednesdayPairs.filter {
                            it.pairNumber == "SEVENTH"
                        }
                    ),
                )
            )

            val thursdaySlots = DayEntity(
                weekDay = "Thursday",
                day = "",
                countClasses = 0,
                timeSlots = listOf(
                    TimeSlotEntity(
                        slotNumber = 1,
                        pairs = thursdayPairs.filter {
                            it.pairNumber == "FIRST"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 2,
                        pairs = thursdayPairs.filter {
                            it.pairNumber == "SECOND"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 3,
                        pairs = thursdayPairs.filter {
                            it.pairNumber == "THIRD"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 4,
                        pairs = thursdayPairs.filter {
                            it.pairNumber == "FOURTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 5,
                        pairs = thursdayPairs.filter {
                            it.pairNumber == "FIFTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 6,
                        pairs = thursdayPairs.filter {
                            it.pairNumber == "SIXTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 7,
                        pairs = thursdayPairs.filter {
                            it.pairNumber == "SEVENTH"
                        }
                    ),
                )
            )

            val fridaySlots = DayEntity(
                weekDay = "Friday",
                day = "",
                countClasses = 0,
                timeSlots = listOf(
                    TimeSlotEntity(
                        slotNumber = 1,
                        pairs = fridayPairs.filter {
                            it.pairNumber == "FIRST"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 2,
                        pairs = fridayPairs.filter {
                            it.pairNumber == "SECOND"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 3,
                        pairs = fridayPairs.filter {
                            it.pairNumber == "THIRD"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 4,
                        pairs = fridayPairs.filter {
                            it.pairNumber == "FOURTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 5,
                        pairs = fridayPairs.filter {
                            it.pairNumber == "FIFTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 6,
                        pairs = fridayPairs.filter {
                            it.pairNumber == "SIXTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 7,
                        pairs = fridayPairs.filter {
                            it.pairNumber == "SEVENTH"
                        }
                    ),
                )
            )

            val saturdaySlots = DayEntity(
                weekDay = "Saturday",
                day = "",
                countClasses = 0,
                timeSlots = listOf(
                    TimeSlotEntity(
                        slotNumber = 1,
                        pairs = saturdayPairs.filter {
                            it.pairNumber == "FIRST"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 2,
                        pairs = saturdayPairs.filter {
                            it.pairNumber == "SECOND"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 3,
                        pairs = saturdayPairs.filter {
                            it.pairNumber == "THIRD"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 4,
                        pairs = saturdayPairs.filter {
                            it.pairNumber == "FOURTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 5,
                        pairs = saturdayPairs.filter {
                            it.pairNumber == "FIFTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 6,
                        pairs = saturdayPairs.filter {
                            it.pairNumber == "SIXTH"
                        }
                    ),
                    TimeSlotEntity(
                        slotNumber = 7,
                        pairs = saturdayPairs.filter {
                            it.pairNumber == "SEVENTH"
                        }
                    ),
                )
            )

            _state.update {
                it.copy(
                    mondayPairs = mondayPairs,
                    tuesdayPairs = tuesdayPairs,
                    wednesdayPairs = wednesdayPairs,
                    thursdayPairs = thursdayPairs,
                    fridayPairs = fridayPairs,
                    saturdayPairs = saturdayPairs,
                    timetable = WeekEntity(
                        days = listOf(
                            mondaySlots, tuesdaySlots, wednesdaySlots, thursdaySlots, fridaySlots, saturdaySlots
                        )
                    )
                )
            }
        } catch (e: Exception) {
            offerEvent(MeetingsScreenEvent.ShowSnackbar((e.localizedMessage ?: "Ошибка сервера")))
        }
    }

    private fun getWeekNumberFromFirstSeptember(week: WeekDateEntity): Int {
        val weekMillis = 1000 * 60 * 60 * 24 * 7f
        val now = Calendar.getInstance()
        now.timeInMillis = week.endDate
        val firstSeptember = Calendar.getInstance()
        firstSeptember.set(firstSeptember.get(Calendar.YEAR), Calendar.SEPTEMBER, 1)
        if (firstSeptember.after(now)) {
            firstSeptember.set(firstSeptember.get(Calendar.YEAR) - 1, Calendar.SEPTEMBER, 1)
        }
        return kotlin.math.ceil((now.timeInMillis - firstSeptember.timeInMillis) / weekMillis).toInt()
    }

    private fun getCurrentDateByWeek(week: WeekDateEntity): String {
        return DateFormat.format("MMMM yyyy · ${getWeekNumberFromFirstSeptember(week)} неделя", week.startDate).toString()
    }

    private fun getCurrentWeek(): WeekDateEntity {
        val milliseconds = 1
        val second = 1000 * milliseconds
        val minute = 60 * second
        val hour = 60 * minute
        val day = 24 * hour
        val week = 7 * day
        val now = Calendar.getInstance()
        val startWeek = now.timeInMillis -
            now.get(Calendar.MILLISECOND) * milliseconds -
            now.get(Calendar.SECOND) * second -
            now.get(Calendar.MINUTE) * minute -
            now.get(Calendar.HOUR_OF_DAY) * hour -
            (now.get(Calendar.DAY_OF_WEEK) - now.firstDayOfWeek) * day
        val endWeek = startWeek + week - day
        return WeekDateEntity(startWeek, endWeek)
    }

    fun getCurrentDay(): Long {
        val milliseconds = 1
        val second = 1000 * milliseconds
        val minute = 60 * second
        val hour = 60 * minute
        val day = 24 * hour
        val week = 7 * day
        val now = Calendar.getInstance()
        return now.timeInMillis -
            now.get(Calendar.MILLISECOND) * milliseconds -
            now.get(Calendar.SECOND) * second -
            now.get(Calendar.MINUTE) * minute -
            now.get(Calendar.HOUR_OF_DAY) * hour
    }

    private fun getLastWeek(currentWeek: WeekDateEntity): WeekDateEntity {
        val week = 1000 * 60 * 60 * 24 * 7
        return WeekDateEntity(
            startDate = currentWeek.startDate - week,
            endDate = currentWeek.endDate - week
        )
    }

    private fun getNextWeek(currentWeek: WeekDateEntity): WeekDateEntity {
        val week = 1000 * 60 * 60 * 24 * 7
        return WeekDateEntity(
            startDate = currentWeek.startDate + week,
            endDate = currentWeek.endDate + week
        )
    }
}

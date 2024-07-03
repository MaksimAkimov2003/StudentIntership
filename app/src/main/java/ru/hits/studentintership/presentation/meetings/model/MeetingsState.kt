package ru.hits.studentintership.presentation.meetings.model

import ru.hits.studentintership.data.meetings.DayMeetingDto
import ru.hits.studentintership.data.meetings.MeetingDto
import ru.hits.studentintership.presentation.meetings.WeekDateEntity

data class MeetingsState(
    val meetings: List<DayMeetingDto>,
    val mondayPairs: List<MeetingDto>,
    val tuesdayPairs: List<MeetingDto>,
    val wednesdayPairs: List<MeetingDto>,
    val thursdayPairs: List<MeetingDto>,
    val fridayPairs: List<MeetingDto>,
    val saturdayPairs: List<MeetingDto>,
    val timetable: WeekEntity,
    val currentDay: Long,
    val currentDate: String,
    val currentWeek: WeekDateEntity,
)

data class WeekEntity (
    val days: List<DayEntity>
)

data class DayEntity (
    val weekDay: String,
    val day: String,
    val countClasses: Int,
    val timeSlots: List<TimeSlotEntity>
)

data class TimeSlotEntity (
    val slotNumber: Int,
    val pairs: List<MeetingDto>?
)
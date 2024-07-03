package ru.hits.studentintership.data.meetings

import kotlinx.serialization.Serializable

@Serializable
data class DayMeetingDto(
    val fridayMeetings: List<MeetingDto>,
    val mondayMeetings: List<MeetingDto>,
    val pairNumber: String,
    val saturdayMeetings: List<MeetingDto>,
    val thursdayMeetings: List<MeetingDto>,
    val tuesdayMeetings: List<MeetingDto>,
    val wednesdayMeetings: List<MeetingDto>
)

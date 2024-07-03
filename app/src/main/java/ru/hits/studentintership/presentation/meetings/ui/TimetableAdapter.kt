package ru.hits.studentintership.presentation.meetings.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import ru.hits.studentintership.data.meetings.DayMeetingDto
import ru.hits.studentintership.data.meetings.MeetingDto
import ru.hits.studentintership.presentation.meetings.model.DayEntity

private data class TimeSlot(val start: String, val end: String)

class TimetableAdapter(private val items: List<DayEntity>, private val context: Context) : ScrollTableAdapter() {

    private val timeslots = listOf(
        TimeSlot("8:45", "10:20"),
        TimeSlot("10:35", "12:10"),
        TimeSlot("12:25", "14:00"),
        TimeSlot("14:45", "16:20"),
        TimeSlot("16:35", "18:10"),
        TimeSlot("18:25", "20:00"),
        TimeSlot("20:15", "21:50")
    )

    override fun drawRowMark(y: Int): CanvasBlock.() -> Unit = {
        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 36.sp.value
            Color.Black.toArgb()
        }
        val start = timeslots[y].start
        val end = timeslots[y].end
        drawText(
            start,
            0f,
            textSpacing / 2 - getDefaultTextHeight(textPaint) / 2,
            textPaint,
            true
        )
        drawText(
            end,
            0f,
            getDefaultTextHeight(textPaint) / 2 - textSpacing / 2,
            textPaint,
            true
        )
    }

    override fun drawColumnMark(x: Int): CanvasBlock.() -> Unit = {
        val weekDayPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 48.sp.value
            color = Color.Black.toArgb()
        }
        val dayPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 36.sp.value
            color = Color.Gray.toArgb()
        }
        val weekDay = items[x].weekDay
        val day = items[x].day
        Log.d("week day", weekDay)
        Log.d("day", day)
        drawText(
            weekDay,
            0f,
            textSpacing / 2 - getDefaultTextHeight(weekDayPaint) / 2,
            weekDayPaint,
            true
        )
        drawText(
            day,
            0f,
            getDefaultTextHeight(dayPaint) / 2 + textSpacing / 2,
            dayPaint,
            true
        )
    }

    override fun drawItem(x: Int, y: Int): CanvasBlock.() -> Unit = {

        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 36.sp.value
            color = Color.Black.toArgb()
        }
        val itemWidth = 500.dp.value
        var offset = Offset(0f, 0f)
        var rectOffset: Offset
        items[x].timeSlots.forEach { slot ->
            if (slot.slotNumber == y + 1) {
                slot.pairs?.forEach { pair ->
                    val rectPaint = Paint().asFrameworkPaint().apply {
                        isAntiAlias = true
                        color = Color.White.toArgb()
                    }
                    rectOffset = Offset(0f, if (offset.y == 0f) 0f else offset.y + 8.dp.value)
                    offset = Offset(rectOffset.x + 16.dp.value, rectOffset.y + 16.dp.value)
                    offset = drawIconAndText(pair.company.name, null, textPaint, offset, itemWidth)
                    offset = Offset(offset.x, offset.y + 16.dp.value)
                    offset = drawIconAndText(pair.audience, null, textPaint, offset, itemWidth)
                    offset = Offset(offset.x, offset.y + 16.dp.value)
                    var groups = ""
                    pair.groups.forEach {
                        groups += if(groups.isNotEmpty()) {
                            ", ${it.name}"
                        } else {
                            it.name
                        }
                    }
                    offset = drawIconAndText(groups, null, textPaint, offset, itemWidth)
                    offset = Offset(offset.x, offset.y + 16.dp.value)
                    drawRoundRectUnderContent(rectOffset.x, rectOffset.y, itemWidth, offset.y, rectPaint)

                }
            }
        }
    }

    private fun CanvasBlock.drawIconAndText(text: String, iconId: Int?, textPaint: android.graphics.Paint, offset: Offset, width: Float): Offset {
        // val bitmapSize = getBitmap(iconId).width
        val textOffset = Offset(offset.x + 8.dp.value, offset.y)
        // drawIcon(
        //     offset.x, offset.y,
        //     offset.x + bitmapSize, offset.y + bitmapSize,
        //     getBitmap(iconId),
        //     textPaint
        // )
        drawText(text, textOffset.x, textOffset.y, textPaint, width - textOffset.x)
        return Offset(offset.x, offset.y + getTextHeight(text, textPaint, width - textOffset.x))
    }

    override fun rowCount(): Int {
        return items.size
    }

    override fun columnCount(): Int {
        return timeslots.size
    }
}

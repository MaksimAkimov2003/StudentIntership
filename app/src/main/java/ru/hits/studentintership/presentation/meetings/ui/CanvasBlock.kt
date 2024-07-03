package ru.hits.studentintership.presentation.meetings.ui

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import java.lang.Float.max

class CanvasBlock {

	private var content: MutableList<DrawScope.() -> Unit> = mutableListOf()
	val textSpacing = 8.dp.value
	val rectRound = 8.dp.value

	var width: Float = 0f
	var height: Float = 0f

	fun DrawScope.display() { content.forEach { it() } }

	fun drawText(text: String, x: Float, y: Float, paint: Paint, toCenter: Boolean = false) {
		content.add {
			val offset = if(toCenter) Offset(width/2f - getTextHeight(text, paint)/2f, height/2f - getDefaultTextHeight(paint)/2) else Offset(0f, 0f)
			drawContext.canvas.nativeCanvas.drawText(
				text,
				offset.x + x,
				offset.y + y + getDefaultTextHeight(paint),
				paint)
		}
		width = max(width, x + getTextHeight(text, paint))
		height = max(height, y + 2 * getDefaultTextHeight(paint))
	}

	private data class Lines(val text: String, val x: Float, val y: Float)
	fun drawText(text: String, x: Float, y: Float, paint: Paint, textWidth: Float) {
		val lines = getLines(text, paint, textWidth)
		lines.forEach {
			content.add {
				drawContext.canvas.nativeCanvas.drawText(
					it.text,
					it.x + x,
					it.y + y + getDefaultTextHeight(paint),
					paint
				)
			}
		}
		width = max(width, x + textWidth)
		height = max(height,  y + getDefaultTextHeight(paint) * lines.size + textSpacing * (lines.size - 2))
	}

	fun drawIcon(left: Float, top: Float, right: Float, bottom: Float, bitmap: Bitmap, paint: Paint) {
		content.add {
			drawContext.canvas.nativeCanvas.drawBitmap(bitmap, left, top, paint)
		}
		width = max(width, right)
		height = max(height, bottom)
	}

	fun drawRoundRectUnderContent(left: Float, top: Float, right: Float, bottom: Float, paint: Paint) {
		content.add(0) {
			drawContext.canvas.nativeCanvas.drawRoundRect(
				left, top, right, bottom,
				rectRound, rectRound,
				paint
			)
		}
		width = max(width, right)
		height = max(height, bottom)
	}

	fun getDefaultTextHeight(paint: Paint): Float {
		val bounds = Rect()
		paint.getTextBounds("1", 0, "1".length, bounds)
		return bounds.height().toFloat() * 1.5f
	}

	fun getTextHeight(text: String, paint: Paint): Float {
		return paint.measureText(text)
	}

	fun getTextHeight(text: String, paint: Paint, maxWidth: Float): Float {
		val lines = getLines(text, paint, maxWidth)
		return if(lines.isEmpty()) 0f else lines.last().y + getDefaultTextHeight(paint)
	}

	private fun getLines(text: String, paint: Paint, maxWidth: Float): List<Lines> {
		val lines = mutableListOf<Lines>()
		val textTokens = text.split(" ")
		var line = ""
		textTokens.forEach {
			if(paint.measureText(line, 0, line.length) + paint.measureText(it, 0, it.length) >= maxWidth) {
				lines.add(Lines(line, 0f, if(lines.isEmpty()) 0f else lines.last().y + getDefaultTextHeight(paint)))
				line = it
			}
			else {
				if(line.isEmpty()) {
					line += it
				}
				else {
					line += " $it"
				}
			}
		}
		if(line.isNotEmpty()) {
			lines.add(Lines(line, 0f, if(lines.isEmpty()) 0f else lines.last().y + getDefaultTextHeight(paint)))
		}
		return lines
	}
}

package ru.hits.studentintership.presentation.meetings.ui

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import java.lang.Float.max
import java.lang.Float.min

class ScrollTableState {

	private var rowMarks = listOf<CanvasBlock>()
	private var columnMarks = listOf<CanvasBlock>()
	private var table = listOf(listOf<CanvasBlock>())

	data class Measurements(
		var row: List<Float> = listOf(),
		var column: List<Float> = listOf(),
		var rowMarkLine: Float = 0f,
		var columnMarkLine: Float = 0f,
		var contentWidth: Float = 0f,
		var contentHeight: Float = 0f,
		var offsetX: Float = 16.dp.value,
		var offsetY: Float = 16.dp.value,
		var viewSizeX: Float = 0.dp.value,
		var viewSizeY: Float = 0.dp.value,
		var strokeWidth: Float = 2.dp.value
	)
	var measurements by mutableStateOf(Measurements())

	private var verticalScrollOffset = 0f
	private var horizontalScrollOffset = 0f
	val verticalScrollState = ScrollableState {
		if(measurements.contentHeight - measurements.viewSizeY - measurements.strokeWidth > 0f) {
			val delta =
				if (it < 0f) 	max(verticalScrollOffset - (measurements.contentHeight - measurements.viewSizeY - measurements.strokeWidth), it)
				else 			min(verticalScrollOffset, it)
			verticalScrollOffset = (verticalScrollOffset - delta)
				.coerceAtLeast(0f)
				.coerceAtMost(measurements.contentHeight - measurements.offsetY)
			val mes = measurements.row.toMutableList()
			for (i in mes.indices) { mes[i] += delta }
			measurements = measurements.copy(row = mes)
		}
		it
	}
	val horizontalScrollState = ScrollableState {
		if(measurements.contentWidth - measurements.viewSizeX - measurements.strokeWidth > 0f) {
			val delta =
				if (it < 0f) 	max(horizontalScrollOffset - (measurements.contentWidth - measurements.viewSizeX - measurements.strokeWidth), it)
				else 			min(horizontalScrollOffset, it)
			horizontalScrollOffset = (horizontalScrollOffset - delta)
				.coerceAtLeast(0f)
				.coerceAtMost(measurements.contentWidth - measurements.viewSizeX)
			val mes = measurements.column.toMutableList()
			for (i in mes.indices) { mes[i] += delta }
			measurements = measurements.copy(column = mes)
		}
		it
	}

	fun setViewSize(x: Float, y: Float) {
		measurements.viewSizeX = x
		measurements.viewSizeY = y
	}

	private fun measureTable() {
		val rowMeasurements = MutableList(rowMarks.size + 1) { 0f }
		val columnMeasurements = MutableList(rowMarks.size + 1) { 0f }
		for (i in 0 .. columnMarks.size) {
			for (j in 0 .. rowMarks.size) {
				when {
					i == 0 && j == 0 	-> {  }
					i == 0 				-> {
						columnMeasurements[i] = max(columnMeasurements[i], rowMarks[j - 1].width + 2 * measurements.offsetX + measurements.strokeWidth)
						rowMeasurements[j] = max(rowMeasurements[j], rowMarks[j - 1].height + 2 * measurements.offsetY + measurements.strokeWidth)
					}
					j == 0			 	-> {
						columnMeasurements[i] = max(columnMeasurements[i], columnMarks[i - 1].width + 2 * measurements.offsetX + measurements.strokeWidth)
						rowMeasurements[j] = max(rowMeasurements[j], columnMarks[i - 1].height + 2 * measurements.offsetY + measurements.strokeWidth)
					}
					else			 	-> {
						columnMeasurements[i] = max(columnMeasurements[i], table[i - 1][j - 1].width + 2 * measurements.offsetX + measurements.strokeWidth)
						rowMeasurements[j] = max(rowMeasurements[j], table[i - 1][j - 1].height + 2 * measurements.offsetY + measurements.strokeWidth)
					}
				}
			}
		}

		for(i in 0 until columnMeasurements.size) {
			for(j in 0 until rowMeasurements.size) {
				when {
					i == 0 && j == 0	-> {  }
					i == 0				-> {
						rowMarks[j-1].height = rowMeasurements[j] - (2 * measurements.offsetY + measurements.strokeWidth)
						rowMarks[j-1].width = columnMeasurements[i] - (2 * measurements.offsetX + measurements.strokeWidth)
					}
					j == 0				-> {
						columnMarks[i-1].height = rowMeasurements[j] - (2 * measurements.offsetY + measurements.strokeWidth)
						columnMarks[i-1].width = columnMeasurements[i] - (2 * measurements.offsetX + measurements.strokeWidth)
					}
					else				-> {
						table[i-1][j-1].height = rowMeasurements[j] - (2 * measurements.offsetY + measurements.strokeWidth)
						table[i-1][j-1].width = columnMeasurements[i] - (2 * measurements.offsetX + measurements.strokeWidth)
					}
				}
			}
			if(i != 0) columnMeasurements[i] += columnMeasurements[i - 1]
		}
		for(j in 1 until rowMeasurements.size) {
			rowMeasurements[j] += rowMeasurements[j - 1]
		}

		measurements.rowMarkLine = columnMeasurements[0]
		measurements.columnMarkLine = rowMeasurements[0]
		measurements.contentHeight = rowMeasurements[rowMeasurements.size - 1]
		measurements.contentWidth = columnMeasurements[columnMeasurements.size - 1]

//		for(i in 0 until columnMeasurements.size) {
//			columnMeasurements[i] -= horizontalScrollOffset
//		}
//		for(j in 0 until rowMeasurements.size) {
//			rowMeasurements[j] -= verticalScrollOffset
//		}

		measurements.row = rowMeasurements
		measurements.column = columnMeasurements
	}

	companion object {

		fun getState(
            rowMarks: List<CanvasBlock>,
            columnMarks: List<CanvasBlock>,
            table: List<List<CanvasBlock>>,
            verticalScrollOffset: Float = 0f,
            horizontalScrollOffset: Float = 0f
		) = ScrollTableState().apply {
			this.rowMarks = rowMarks
			this.columnMarks = columnMarks
			this.table = table
			this.verticalScrollOffset = verticalScrollOffset
			this.horizontalScrollOffset = horizontalScrollOffset
			measureTable()
		}

		@Suppress("UNCHECKED_CAST")
		val Saver: Saver<ScrollTableState, Any> = listSaver(
			save = { listOf(it.rowMarks, it.columnMarks, it.table, it.verticalScrollOffset, it.horizontalScrollOffset) },
			restore = {
				getState(
					rowMarks = it[0] as List<CanvasBlock>,
					columnMarks = it[1] as List<CanvasBlock>,
					table = it[2] as List<List<CanvasBlock>>,
					verticalScrollOffset = it[3] as Float,
					horizontalScrollOffset = it[4] as Float
				)
			}
		)
	}
}


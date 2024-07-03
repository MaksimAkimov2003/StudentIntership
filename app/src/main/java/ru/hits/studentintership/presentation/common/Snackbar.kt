package ru.hits.studentintership.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorSnackbar(
  snackbarHostState: SnackbarHostState,
  modifier: Modifier = Modifier
) {
  Snackbar(
    snackbarHostState = snackbarHostState,
    textColor = MaterialTheme.colorScheme.onPrimary,
    backgroungColor = MaterialTheme.colorScheme.error,
    modifier = modifier
  )
}

@Composable
fun InfoSnackbar(
  snackbarHostState: SnackbarHostState,
  modifier: Modifier = Modifier
) {
  Snackbar(
    snackbarHostState = snackbarHostState,
    textColor = MaterialTheme.colorScheme.primary,
    backgroungColor = MaterialTheme.colorScheme.onPrimary,
    modifier = modifier,
    borderColor = MaterialTheme.colorScheme.primary
  )
}

@Composable
private fun Snackbar(
  snackbarHostState: SnackbarHostState,
  textColor: Color,
  backgroungColor: Color,
  modifier: Modifier = Modifier,
  borderColor: Color = backgroungColor
) {
  SnackbarHost(hostState = snackbarHostState, modifier = modifier, snackbar = { snackbarData ->
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 48.dp)
        .background(backgroungColor, shape = RoundedCornerShape(8.dp))
        .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
        .padding(start = 16.dp, end = 8.dp, top = 16.dp, bottom = 16.dp),
      contentAlignment = Alignment.CenterStart
    ) {
      Text(text = snackbarData.visuals.message, color = textColor, maxLines = 2)
    }
  })
}

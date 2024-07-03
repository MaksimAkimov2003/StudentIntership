package ru.hits.studentintership.presentation.launch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FullscreenLoading() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.Black.copy(alpha = 0.35f))
      .clickable(enabled = false, onClick = {}),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .background(color = Color.Black.copy(alpha = 0.35f))
      .clickable(enabled = false, onClick = {}),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

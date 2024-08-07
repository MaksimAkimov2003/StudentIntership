package ru.hits.studentintership.presentation.photo.components

import android.view.ViewGroup
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.camera.view.PreviewView.ScaleType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraPreview(
  modifier: Modifier = Modifier,
  scaleType: ScaleType = ScaleType.FILL_CENTER,
  onUseCase: (UseCase) -> Unit = { }
) {
  AndroidView(
    modifier = modifier,
    factory = { context ->
      val previewView = PreviewView(context).apply {
        this.scaleType = scaleType
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
      }
      onUseCase(
        Preview.Builder()
          .build()
          .also {
            it.setSurfaceProvider(previewView.surfaceProvider)
          }
      )
      previewView
    }
  )
}

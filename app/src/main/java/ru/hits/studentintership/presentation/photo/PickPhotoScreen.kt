package ru.hits.studentintership.presentation.photo

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.studentintership.presentation.photo.components.GallerySelect
import ru.hits.studentintership.presentation.photo.components.MyCameraX

@Composable
fun PickPhotoScreen(
  navigateToSolutionScreen: (solutionId: String?, taskId: String, uri: Uri?) -> Unit,
  viewModel: PickPhotoViewModel = hiltViewModel()
) {
  var showGallerySelect by remember { mutableStateOf(false) }
  if (showGallerySelect) {
    GallerySelect(
      onImageUri = { uri -> navigateToSolutionScreen(viewModel.solutionId, viewModel.taskId, uri) },
      onPermissionDeny = {
        showGallerySelect = false
      }
    )
  }
  Box(modifier = Modifier.fillMaxSize()) {
    MyCameraX(
      onImageFile = { file -> navigateToSolutionScreen(viewModel.solutionId, viewModel.taskId, file?.toUri()) },
      viewModel
    )
    // CameraCapture(
    //   modifier = Modifier.fillMaxSize(),
    //   onImageFile = { file -> onNavigateBack(file?.toUri()) },
    //   onPermissionDeny = { onNavigateBack(null) },
    //   onCameraProvideFailure = { onNavigateBack(null) }
    // )
    IconButton(
      modifier = Modifier
        .navigationBarsPadding()
        .size(100.dp)
        .padding(16.dp)
        .align(Alignment.BottomStart),
      onClick = { showGallerySelect = true }
    ) {
      Icon(
        imageVector = Icons.Rounded.List,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
          .size(100.dp)
          .padding(1.dp)
      )
    }
  }
}

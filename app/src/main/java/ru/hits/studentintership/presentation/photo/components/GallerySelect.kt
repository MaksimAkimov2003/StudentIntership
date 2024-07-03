package ru.hits.studentintership.presentation.photo.components

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun GallerySelect(
  onImageUri: (Uri?) -> Unit = { },
  onPermissionDeny: () -> Unit = { }
) {
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent(),
    onResult = { uri -> onImageUri(uri) }
  )

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    Permission(
      permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
      dialogTitle = "Доступ к функционалу",
      dialogMessage = "Доступ к функционалу очень важен для нашего приложения. Пожалуйста, предоставьте доступ.",
      openSettingsText = "Доступ к этому функционалу необходим для работы приложения. Пожалуйста, предоставьте доступ в настройках.",
      onPermissionDeny = onPermissionDeny
    ) {
      LaunchGallery(launcher)
    }
  } else {
    LaunchGallery(launcher)
  }
}

@Composable
private fun LaunchGallery(launcher: ManagedActivityResultLauncher<String, Uri?>) {
  LaunchedEffect(key1 = Unit) {
    launcher.launch("image/*")
  }
}

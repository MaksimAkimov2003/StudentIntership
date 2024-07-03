package ru.hits.studentintership.presentation.photo.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import coil.compose.rememberImagePainter
import ru.hits.studentintership.R
import ru.hits.studentintership.presentation.photo.PickPhotoViewModel

@Composable
fun MyCameraX(
    onImageFile: (File?) -> Unit,
    viewModel: PickPhotoViewModel,
) {
    val lifecycleObserver = LocalLifecycleOwner.current// Create a life cycle owner
    val context = LocalContext.current

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    // Create a camera preview view
    val previewView = remember {
        PreviewView(context).apply {
            id = R.id.preview_view// Specify the ID created in XML
        }
    }

    // ImageCapture is a case of image capture. Submit the takePiction function to shoot the picture to the memory or save it to the file, and provide the original image data
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    // Display pictures on the interface, save URI status
    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val fileUtils: FileUtils by lazy { FileUtilsImpl() }
    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            factory = { previewView },// Add the camera preview view
            modifier = Modifier.fillMaxSize()
        ) {
            cameraProviderFuture.addListener(
                {// Add a monitor
                    val cameraProvider = cameraProviderFuture.get() // Create CameraProvider
                    // Build a camera preview object
                    val preview = androidx.camera.core.Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    // Set the camera and use the camera on the back default
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    try {
                        // From the life cycle of the application, the camera binding of all applications will be lifted, which will turn off all currently opened cameras and run it when starting
                        cameraProvider.unbindAll()
                        // Binding life cycle
                        cameraProvider.bindToLifecycle(
                            lifecycleObserver,
                            cameraSelector,
                            preview, imageCapture
                        )
                    } catch (e: Exception) {
                        Log.e("Camera", e.toString())
                    }

                },
                ContextCompat.getMainExecutor(context)// Add to the main actuator
            )
        }


        // Store the image into the file
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Show the picture of taking pictures
            imageUri.value?.let {
                Image(
                    painter = rememberAsyncImagePainter(model = it),
                    contentDescription = "",
                    modifier = Modifier.size(60.dp)
                )
            }
            Spacer(modifier = Modifier.width(24.dp))

            IconButton(onClick = {
                fileUtils.createDirectoryIfNotExist(context)
                val file = fileUtils.createFile(context)

                // Options for storing new capture images OutputOptions
                val outputOption = ImageCapture.OutputFileOptions.Builder(file).build()
                imageCapture.takePicture(outputOption,
                    ContextCompat.getMainExecutor(context),// Call the thread actuator
                    object : ImageCapture.OnImageSavedCallback { // For newly captured image call recovery
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val saveUri = Uri.fromFile(file)// Save image address
                            Toast.makeText(context, saveUri.path, Toast.LENGTH_SHORT).show()
                            imageUri.value = saveUri// Set the image display path
                            viewModel.onPhotoClick(saveUri)
                            onImageFile(file)
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e("Camera", "$exception")
                        }

                    }
                )
            }) {
                Text(text = "Photograph")
            }
        }

    }
}

interface FileUtils {
    // Create a file directory
    fun createDirectoryIfNotExist(context: Context)

    //Create a file
    fun createFile(context: Context): File
}

class FileUtilsImpl : FileUtils {
    companion object {
        private const val IMAGE_PREFIX = "Image_"
        private const val JPG_SUFFIX = ".jpg"
        private const val FOLDER_NAME = "Photo"
    }

    override fun createDirectoryIfNotExist(context: Context) {
        val folder = File(
            "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absoluteFile}"
                + File.separator
                + FOLDER_NAME
        )
        if (!folder.exists()) {
            folder.mkdir()
        }
    }

    override fun createFile(context: Context) = File(
        "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absoluteFile}"
            + File.separator + FOLDER_NAME + File.separator + IMAGE_PREFIX + System.currentTimeMillis() + JPG_SUFFIX
    )

}

@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageFile: (File?) -> Unit = { },
    onPermissionDeny: () -> Unit = { },
    onCameraProvideFailure: () -> Unit = { },
) {
    val context = LocalContext.current
    CameraPermissionWrapper(onPermissionDeny = onPermissionDeny) {
        Box(modifier = modifier) {
            val lifecycleOwner = LocalLifecycleOwner.current
            val coroutineScope = rememberCoroutineScope()
            var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
            val imageCaptureUseCase by remember {
                mutableStateOf(
                    ImageCapture.Builder()
                        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build()
                )
            }
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onUseCase = { previewUseCase = it }
            )
            IconButton(
                modifier = Modifier
                    .navigationBarsPadding()
                    .size(100.dp)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    // coroutineScope.launch {
                    //     imageCaptureUseCase.takePicture(context.executor).also {
                    //         onImageFile(it)
                    //         Log.d("file", (it?.name + it?.path))
                    //     }
                    // }
                }
            ) {
                Icon(
                    imageVector = Icons.Sharp.Done,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                )
            }
            LaunchedEffect(key1 = previewUseCase, key2 = imageCaptureUseCase) {
                val cameraProvider = context.getCameraProvider()
                runCatching {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                    )
                }.onFailure {
                    onCameraProvideFailure()
                }
            }
        }
    }
}

@Composable
private fun CameraPermissionWrapper(
    onPermissionDeny: () -> Unit,
    content: @Composable () -> Unit
) {
    Permission(
        permission = Manifest.permission.CAMERA,
        dialogTitle = "Доступ к функционалу",
        dialogMessage = "Доступ к функционалу очень важен для нашего приложения. Пожалуйста, предоставьте доступ.",
        openSettingsText = "Доступ к этому функционалу необходим для работы приложения. Пожалуйста, предоставьте доступ в настройках.",
        onPermissionDeny = onPermissionDeny
    ) {
        content()
    }
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener(
            {
                continuation.resume(future.get())
            },
            executor
        )
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

// suspend fun ImageCapture.takePicture(executor: Executor): File? {
//
//     val photoFile = File(
//         outputDirectory,
//         SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
//     )
//
//     val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//     imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
//         override fun onError(exception: ImageCaptureException) {
//             Log.e("kilo", "Take photo error:", exception)
//             onError(exception)
//         }
//
//         override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//             val savedUri = Uri.fromFile(photoFile)
//             onImageCaptured(savedUri)
//         }
//     })
// }

@Composable
private fun getOutputDirectory(): File {
    val context = LocalContext.current
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, "new_file").apply { mkdirs() }
    }

    return mediaDir!!
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(
    permission: String,
    dialogTitle: String,
    dialogMessage: String,
    openSettingsText: String,
    onPermissionDeny: () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    var areSettingsNeed by remember { mutableStateOf(false) }
    val permissionState = rememberExtendedPermissionState(
        permission = permission,
        onCannotRequestPermission = {
            areSettingsNeed = true
        },
        onPermissionResult = { granted ->
            if (!granted && !areSettingsNeed) {
                onPermissionDeny()
            }
        }
    )

    LaunchedEffect(key1 = permissionState.status, areSettingsNeed) {
        if (isAskEveryTime(permissionState, areSettingsNeed)) {
            permissionState.launchPermissionRequest()
        }
    }

    when {
        permissionState.status.isGranted -> content()

        areSettingsNeed -> {
            val context = LocalContext.current
            val applicationDetailsSettingsLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) {
                if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    areSettingsNeed = false
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun isAskEveryTime(permissionState: ExtendedPermissionState, needSettings: Boolean): Boolean =
    (permissionState.status.isGranted || permissionState.status.shouldShowRationale || needSettings).not()

@ExperimentalPermissionsApi
@Composable
fun rememberExtendedPermissionState(
    permission: String,
    onCannotRequestPermission: () -> Unit = {},
    onPermissionResult: (Boolean) -> Unit = {},
): ExtendedPermissionState {
    val activity = LocalContext.current as Activity

    var currentShouldShowRationale by remember {
        mutableStateOf(activity.shouldShowRequestPermissionRationale(permission))
    }

    var atDoubleDenialForPermission by remember {
        mutableStateOf(false)
    }

    val mutablePermissionState = com.google.accompanist.permissions.rememberPermissionState(permission) { isGranted ->
        if (!isGranted) {
            val updatedShouldShowRationale = activity.shouldShowRequestPermissionRationale(permission)
            if (!currentShouldShowRationale && !updatedShouldShowRationale) {
                onCannotRequestPermission()
            } else if (currentShouldShowRationale && !updatedShouldShowRationale) {
                atDoubleDenialForPermission = true
            }
        }
        onPermissionResult(isGranted)
    }

    return remember(permission) {
        ExtendedPermissionState(
            permission = permission,
            mutablePermissionState = mutablePermissionState,
            onCannotRequestPermission = onCannotRequestPermission,
            atDoubleDenial = atDoubleDenialForPermission,
            onLaunchedPermissionRequest = {
                currentShouldShowRationale = it
            }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Stable
class ExtendedPermissionState(
    override val permission: String,
    private val mutablePermissionState: PermissionState,
    private val atDoubleDenial: Boolean,
    private val onLaunchedPermissionRequest: (shouldShowRationale: Boolean) -> Unit,
    private val onCannotRequestPermission: () -> Unit
) : PermissionState {
    override val status: PermissionStatus
        get() = mutablePermissionState.status

    override fun launchPermissionRequest() {
        onLaunchedPermissionRequest(mutablePermissionState.status.shouldShowRationale)
        if (atDoubleDenial) {
            onCannotRequestPermission()
        } else {
            mutablePermissionState.launchPermissionRequest()
        }
    }
}

package ru.hits.studentintership.presentation.launch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.studentintership.presentation.launch.model.LaunchEvent

@Composable
fun LaunchScreen(
    navigateToProfile: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: LaunchViewModel = hiltViewModel()
) {
    viewModel.screenEvents.CollectEvent { event ->
        when (event) {
            is LaunchEvent.OnAuthorized -> navigateToProfile()
            is LaunchEvent.OnUnauthorized -> navigateToLogin()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.auth()
    }

    FullscreenLoading()
}
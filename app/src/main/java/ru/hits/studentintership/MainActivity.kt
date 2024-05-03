package ru.hits.studentintership

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.hits.studentintership.common.ui.components.AppFilledButton
import ru.hits.studentintership.common.ui.theme.StudentIntershipTheme
import ru.hits.studentintership.login.presentation.LoginScreenState
import ru.hits.studentintership.login.ui.LoginScreenStateless

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentIntershipTheme {
                LoginScreenStateless(
                    state = LoginScreenState(
                        email = "",
                        password = "",
                        isLoading = false
                    ),
                    updateEmailText = {},
                    updatePasswordText = {},
                    onLoginClick = {}
                )
            }
        }
    }
}

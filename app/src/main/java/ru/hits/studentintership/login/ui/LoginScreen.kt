package ru.hits.studentintership.login.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hits.studentintership.R
import ru.hits.studentintership.common.ui.components.AppFilledButton
import ru.hits.studentintership.common.ui.components.AppTextField
import ru.hits.studentintership.common.ui.theme.StudentIntershipTheme
import ru.hits.studentintership.login.presentation.LoginScreenState
import ru.hits.studentintership.login.presentation.LoginViewModel

@Composable
fun LoginScreen(
    navigateNext: () -> Unit
) {

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreenStateless(
    state: LoginScreenState,
    updateEmailText: (newValue: String) -> Unit,
    updatePasswordText: (newValue: String) -> Unit,
    onLoginClick: () -> Unit
) {
    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Email
            AppTextField(
                text = state.email,
                updateText = updateEmailText,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                labelText = stringResource(R.string.e_mail),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        defaultKeyboardAction(ImeAction.Next)
                    }
                )
            )

            // Password
            var isPasswordVisible by rememberSaveable {
                mutableStateOf(false)
            }
            AppTextField(
                text = state.password,
                updateText = updatePasswordText,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                labelText = stringResource(R.string.password),
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            isPasswordVisible = !isPasswordVisible
                        },
                        imageVector = if (isPasswordVisible) {
                            ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
                        } else {
                            ImageVector.vectorResource(id = R.drawable.ic_visibility_on)
                        },
                        contentDescription = null
                    )
                },
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            )

            Spacer(modifier = Modifier.weight(1f))

            AppFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                onClick = onLoginClick
            ) {
                Text(
                    text = stringResource(R.string.login)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
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
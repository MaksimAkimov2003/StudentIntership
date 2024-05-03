package ru.hits.studentintership.common.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.hits.studentintership.common.ui.theme.StudentIntershipTheme

@Composable
fun AppFilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun AppFilledButtonPreview() {
    StudentIntershipTheme {
        AppFilledButton(
            onClick = {},
            content = { Text(text = "Button") }
        )
    }
}
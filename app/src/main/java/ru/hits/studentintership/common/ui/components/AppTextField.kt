package ru.hits.studentintership.common.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hits.studentintership.common.ui.theme.StudentIntershipTheme

@Composable
fun AppTextField(
    text: String,
    updateText: (newValue: String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String?,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = updateText,
        label = if (labelText != null) {
            {
                Text(
                    text = labelText,
                )
            }
        } else {
            null
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
        ),
        trailingIcon = trailingIcon,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    StudentIntershipTheme {
        AppTextField(
            text = "" ,
            updateText = {},
            labelText = "Name",
        )
    }
}
package dev.havlicektomas.scratchcard.detail.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    errorMessage: String,
    onRetry: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Error")
        },
        text = {
            Text(text = errorMessage)
        },
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                onClick = onRetry
            ) {
                Text("Retry")
            }
        }
    )
}
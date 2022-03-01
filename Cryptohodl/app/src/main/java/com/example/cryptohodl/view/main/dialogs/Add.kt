package com.example.cryptohodl.view.main.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun addDialog(onDismissClicked: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
                onDismissClicked()
            },
            title = {
                Text(
                    text = "AlertDialog title"
                )
            },
            text = {
                Text(text = "Alert dialog test")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDismissClicked()
                    }
                ) {
                    Text(text = "Confirm", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDismissClicked()
                    }
                ) {
                    Text(text = "Dismiss dialog", color = Color.Black)
                }
            },
            backgroundColor = Color.Green,
            contentColor = Color.White
        )
    }
}
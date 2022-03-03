package com.example.cryptohodl.view.main.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun settingsDialog(onDismissClicked: (String) -> Unit, currency: String) {
    val openDialog = remember { mutableStateOf(true) }
    val properties = DialogProperties()
    val currencyState = remember { mutableStateOf(TextFieldValue()) }

    if (openDialog.value) {
        Dialog(
            onDismissRequest = {
                openDialog.value = false
                onDismissClicked("")
            },
            properties = properties,
            content = {
                Column {
                    TextField(
                        value = currencyState.value,
                        onValueChange = {
                            currencyState.value = it
                        },
                        label = {
                            Text("Currency")
                        },
                        placeholder = {
                            Text(currency)
                        }
                    )
                    Button(onClick = {
                        onDismissClicked(currencyState.value.text)
                    }) {
                        Text("SAVE")
                    }
                }
            }
        )
    }
}
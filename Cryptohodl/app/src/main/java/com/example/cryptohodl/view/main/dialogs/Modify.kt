package com.example.cryptohodl.view.main.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cryptohodl.HodlApp
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.toaster

@Composable
fun modifyDialog(
    onDismissClicked: (Int) -> Unit,
    onSaveClicked: (asset: Asset) -> Unit,
    asset: Asset,
    currency: String,
) {

    val openDialog = remember { mutableStateOf(true) }
    val properties = DialogProperties()
    val tickerState = remember { mutableStateOf(TextFieldValue()) }
    val investedState = remember { mutableStateOf(TextFieldValue()) }
    val valueState = remember { mutableStateOf(TextFieldValue()) }

    if (openDialog.value) {
        Dialog(
            onDismissRequest = {
                openDialog.value = false
                onDismissClicked(0)
            },
            properties = properties,
            content = {
                Column {
                    TextField(
                        value = tickerState.value,
                        onValueChange = {
                            tickerState.value = it
                        },
                        label = {
                            Text("Ticker")
                        },
                        placeholder = {
                            Text(asset.ticker)
                        }
                    )
                    Row {
                        TextField(
                            value = investedState.value,
                            onValueChange = {
                                investedState.value = it
                            },
                            label = {
                                Text("Invested")
                            },
                            placeholder = {
                                Text(asset.invested.toString())
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(currency)
                    }
                    Row {
                        TextField(
                            value = valueState.value,
                            onValueChange = {
                                valueState.value = it
                            },
                            label = {
                                Text("Value")
                            },
                            placeholder = {
                                Text(asset.value.toString())
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(currency)
                    }
                    Button(onClick = {
                        if (tickerState.value.text != "" && investedState.value.text != "" && valueState.value.text != "") {
                            val newAsset = Asset(
                                ticker = tickerState.value.text,
                                invested = investedState.value.text.toLong(),
                                value = valueState.value.text.toLong()
                            )
                            onSaveClicked(newAsset)
                            onDismissClicked(0)
                        } else {
                            toaster(HodlApp.context, "Fill all fields to save")
                        }
                    }) {
                        Text("SAVE")
                    }
                }
            }
        )
    }
}
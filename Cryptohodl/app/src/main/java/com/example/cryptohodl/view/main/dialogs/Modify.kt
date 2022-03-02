package com.example.cryptohodl.view.main.dialogs

import android.util.Log
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
    onDismissClicked: (String) -> Unit,
    onSaveClicked: (Asset) -> Unit,
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
                onDismissClicked("")
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
                        val ticker = if(tickerState.value.text != ""){
                            tickerState.value.text
                        } else {
                            asset.ticker
                        }

                        val invested = if(investedState.value.text != ""){
                            investedState.value.text.toLong()
                        } else {
                            asset.invested
                        }

                        val value = if(valueState.value.text != ""){
                            valueState.value.text.toLong()
                        } else {
                            asset.value
                        }

                        val newAsset = Asset(
                            ticker = ticker,
                            invested = invested,
                            value = value,
                            id = asset.id
                        )

                        onSaveClicked(newAsset)
                        onDismissClicked("")
                    }) {
                        Text("SAVE")
                    }
                }
            }
        )
    }
}
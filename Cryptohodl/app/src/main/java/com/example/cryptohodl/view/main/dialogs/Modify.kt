package com.example.cryptohodl.view.main.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.view.ui.theme.CustomTheme
import com.example.cryptohodl.view.ui.theme.rounded
import java.util.*

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
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    CustomTheme.colors.primaryVariant,
                                    CustomTheme.colors.secondaryVariant,
                                ),
                                start = Offset.Zero,
                                end = Offset(Float.POSITIVE_INFINITY.times(0.5f), Float.POSITIVE_INFINITY.times(0.5f))
                            ),
                            shape = rounded
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                    {
                        Text(
                            "Mettre à jour votre investissement en ${asset.ticker}",
                            color = Color.White,
                            style = CustomTheme.typography.big,
                            textAlign = TextAlign.Center
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextField(
                                value = tickerState.value,
                                onValueChange = {
                                    tickerState.value = it
                                },
                                label = {
                                    Text("Symbole", color = CustomTheme.colors.gainDefault)
                                },
                                placeholder = {
                                    Text(asset.ticker, color = Color.Black)
                                },
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.White,
                                    disabledTextColor = Color.Transparent,
                                    backgroundColor = CustomTheme.colors.surfaceA,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = CustomTheme.colors.gainDefault
                                ),
                                shape = rounded,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Text(
                                text = "",
                                modifier = Modifier.weight(0.12f),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                style = CustomTheme.typography.medium
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextField(
                                value = investedState.value,
                                onValueChange = {
                                    investedState.value = it
                                },
                                label = {
                                    Text("Valeur investie", color = CustomTheme.colors.gainDefault)
                                },
                                placeholder = {
                                    Text(asset.invested.toString(), color = Color.Black)
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.White,
                                    disabledTextColor = Color.Transparent,
                                    backgroundColor = CustomTheme.colors.surfaceA,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = CustomTheme.colors.gainDefault
                                ),
                                shape = rounded,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Text(
                                text = currency,
                                modifier = Modifier.weight(0.12f),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                style = CustomTheme.typography.medium
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextField(
                                value = valueState.value,
                                onValueChange = {
                                    valueState.value = it
                                },
                                label = {
                                    Text("Valeur actuelle", color = CustomTheme.colors.gainDefault)
                                },
                                placeholder = {
                                    Text(asset.value.toString(), color = Color.Black)
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.White,
                                    disabledTextColor = Color.Transparent,
                                    backgroundColor = CustomTheme.colors.surfaceA,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = CustomTheme.colors.gainDefault
                                ),
                                shape = rounded,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Text(
                                text = currency,
                                modifier = Modifier.weight(0.12f),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                style = CustomTheme.typography.medium
                            )
                        }
                        Button(
                            onClick = {
                                val ticker = if (tickerState.value.text != "") {
                                    tickerState.value.text
                                } else {
                                    asset.ticker
                                }

                                val invested = if (investedState.value.text != "") {
                                    investedState.value.text.toLong()
                                } else {
                                    asset.invested
                                }

                                val value = if (valueState.value.text != "") {
                                    valueState.value.text.toLong()
                                } else {
                                    asset.value
                                }

                                val newAsset = Asset(
                                    ticker = ticker.uppercase(Locale.getDefault()),
                                    invested = invested,
                                    value = value,
                                    id = asset.id
                                )

                                onSaveClicked(newAsset)
                                onDismissClicked("")
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            shape = rounded
                        ) {
                            Text("Enregistrer", style = CustomTheme.typography.th, color = CustomTheme.colors.primary)
                        }
                    }
                }

            }
        )
    }
}
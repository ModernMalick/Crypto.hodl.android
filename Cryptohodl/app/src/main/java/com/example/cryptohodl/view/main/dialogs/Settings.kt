package com.example.cryptohodl.view.main.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cryptohodl.view.ui.theme.CustomTheme
import com.example.cryptohodl.view.ui.theme.rounded

@Composable
fun settingsDialog(onDismissClicked: (String) -> Unit,
                   currency: String,
                   rate: () -> Unit){
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
                ){
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "Paramètres",
                            color = Color.White,
                            style = CustomTheme.typography.big,
                            textAlign = TextAlign.Center
                        )
                        TextField(
                            value = currencyState.value,
                            onValueChange = {
                                if(it.text.length <= 1){
                                    currencyState.value = it
                                }
                            },
                            label = {
                                Text("Devise", color = CustomTheme.colors.gainDefault)
                            },
                            placeholder = {
                                Text(currency, color = Color.Black)
                            },
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
                        )

                        Text(text = "Noter Crypto.hodl",
                            style = CustomTheme.typography.medium,
                            color = Color.White,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                rate()
                        })

                        Text(text = "Mon site web: modernmalick.com",
                            style = CustomTheme.typography.medium,
                            color = CustomTheme.colors.gainDefault)

                        Text(text = "Icônes provenant de flaticon.com",
                            style = CustomTheme.typography.medium,
                            color = CustomTheme.colors.gainDefault)

                        Button(onClick = {
                            onDismissClicked(currencyState.value.text)
                        },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            shape = rounded) {
                            Text("Enregistrer", style = CustomTheme.typography.th, color = CustomTheme.colors.primary)
                        }
                    }
                }
            }
        )
    }
}
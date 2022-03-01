package com.example.cryptohodl.view.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptohodl.R

@Composable
fun header(
    value: Long,
    invested: Long,
    gainsPercentage: Long,
    gainsFiat: Long,
    image: Int,
    currency: String,
){
    var color: Color = Color.Gray
    if(gainsFiat > 0){
        color = Color.Green
    } else if(gainsFiat < 0) {
        color = Color.Red
    }
    Row {
        Column {
            Text("Welcome to your portfolio !")
            Row {
                Text("Current value: ")
                Text("$value$currency", color = color)
            }
            Text("Invested: $invested$currency")
            Row {
                Text("Gains: ")
                Text("$gainsPercentage% ($gainsFiat$currency)", color = color)
            }
        }
        Image(painterResource(image), "YAY")
    }
}

@Preview(showBackground = true)
@Composable
fun headerPreview() {
    header(2065, 1920, 8, 145, R.drawable.placeholder, "$")
}
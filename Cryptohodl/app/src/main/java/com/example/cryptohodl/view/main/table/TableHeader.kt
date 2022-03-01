package com.example.cryptohodl.view.main.table

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun tableHeader(){
    Row {
        Text("Ticker")
        Text("Invested")
        Text("Current")
        Text("Gain")
    }
}

@Preview(showBackground = true)
@Composable
fun tableHeaderPreview() {
    tableHeader()
}
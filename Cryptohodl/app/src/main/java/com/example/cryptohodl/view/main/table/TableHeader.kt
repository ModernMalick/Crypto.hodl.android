package com.example.cryptohodl.view.main.table

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptohodl.view.ui.theme.CustomTheme

@Composable
fun tableHeader() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    ) {
        Text("Symbole", color = Color.White, style = CustomTheme.typography.th, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text("Investi", color = Color.White, style = CustomTheme.typography.th, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text("Actuel", color = Color.White, style = CustomTheme.typography.th, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text("Gain", color = Color.White, style = CustomTheme.typography.th, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text("", modifier = Modifier
            .padding(horizontal = 12.dp)
            .width(36.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun tableHeaderPreview() {
    tableHeader()
}
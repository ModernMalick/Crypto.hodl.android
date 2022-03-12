package com.example.cryptohodl.view.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptohodl.R
import com.example.cryptohodl.view.ui.theme.CustomTheme
import com.example.cryptohodl.view.ui.theme.rounded

@Composable
fun header(
    value: Long,
    invested: Long,
    gainsPercentage: Long,
    gainsFiat: Long,
    image: Int,
    currency: String,
) {
    var color: Color = CustomTheme.colors.gainDefault
    if (gainsFiat > 0) {
        color = CustomTheme.colors.gainGood
    } else if (gainsFiat < 0) {
        color = CustomTheme.colors.gainBad
    }
    Column(modifier = Modifier
        .background(color = CustomTheme.colors.surfaceA, rounded)
        .fillMaxWidth()
        .padding(top = 36.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
        {
            Text("Bienvenue sur votre portfolio !", color = Color.White, style = CustomTheme.typography.big)
        Row(
            horizontalArrangement = Arrangement.spacedBy(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                    ,
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row {
                    Text("Valeur actuelle: ", color = Color.White, style = CustomTheme.typography.medium)
                    Text("$value$currency", color = color, style = CustomTheme.typography.medium)
                }
                Text("Valeur investie: $invested$currency", color = Color.White, style = CustomTheme.typography.small)
                Row {
                    Text("Gains: ", color = Color.White, style = CustomTheme.typography.small)
                    Text("$gainsPercentage% ($gainsFiat$currency)", color = color, style = CustomTheme.typography.small)
                }
            }
            Image(painterResource(image), "Portfolio status",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun headerPreview() {
    header(2065, 1920, 8, 145, R.drawable.placeholder, "$")
}
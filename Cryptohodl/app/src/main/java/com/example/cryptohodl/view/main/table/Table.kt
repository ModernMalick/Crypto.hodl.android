package com.example.cryptohodl.view.main.table

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.view.ui.theme.CustomTheme
import com.example.cryptohodl.view.ui.theme.rounded

@Composable
fun assetTable(
    assets: List<Asset>,
    currency: String,
    onDeleteClick: (Int) -> Unit,
    onRowClick: (String) -> Unit,
    showModify: String,
    onSaveClicked: (Asset) -> Unit,
) {
    Column ( modifier = Modifier
                .background(color = CustomTheme.colors.surfaceA, rounded)
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tableHeader()
        if(assets.isEmpty()){
            Text(text ="Veuillez ajouter un investissement ci-dessous afin de calculer vos gains",
                style = CustomTheme.typography.small,
                color = CustomTheme.colors.gainDefault,
                textAlign = TextAlign.Center)
        } else {
            Column (modifier = Modifier
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                assets.forEach { asset ->
                    val gain = asset.value - asset.invested
                    var color = CustomTheme.colors.gainDefault
                    if (gain > 0) {
                        color = CustomTheme.colors.gainGood
                    } else if (gain < 0) {
                        color = CustomTheme.colors.gainBad
                    }
                    tableRow(asset, gain, color, currency, onDeleteClick, onRowClick, showModify, onSaveClicked)
                }
            }
        }
    }
}
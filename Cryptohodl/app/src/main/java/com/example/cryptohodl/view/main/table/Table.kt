package com.example.cryptohodl.view.main.table

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.cryptohodl.model.Asset

@Composable
fun assetTable(
    assets: List<Asset>,
    currency: String,
    onDeleteClick: (Int) -> Unit,
    onRowClick: (String) -> Unit,
    showModify: String,
    onSaveClicked: (Asset) -> Unit,
) {
    Column {
        tableHeader()
        assets.forEach { asset ->
            val gain = asset.value - asset.invested
            var color = Color.Gray
            if (gain > 0) {
                color = Color.Green
            } else if (gain < 0) {
                color = Color.Red
            }
            tableRow(asset, gain, color, currency, onDeleteClick, onRowClick, showModify, onSaveClicked)
        }
    }
}
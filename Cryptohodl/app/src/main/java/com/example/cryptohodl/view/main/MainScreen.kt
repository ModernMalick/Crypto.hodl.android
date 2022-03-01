package com.example.cryptohodl.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.view.main.table.assetTable
import com.example.cryptohodl.view.main.table.tableRow

@Composable
fun homeScreen(
    assets: List<Asset>,
    value: Long,
    invested: Long,
    gainsPercentage: Long,
    gainsFiat: Long,
    image: Int,
    currency: String,
    onDeleteClick: (Int?) -> Unit,
    onAddClick: () -> Unit,
    onSettingsClicked: () -> Unit,
    onRowClicked: (Int?) -> Unit,
){
    Column {
        header(value, invested, gainsPercentage, gainsFiat, image, currency)
        assetTable(assets, currency, onDeleteClick, onRowClicked)
        Button(onClick = {
            onAddClick()
        }) {
            Text("NEW")
        }
        Button(onClick = {
            onSettingsClicked()
        }) {
            Text("SETTINGS")
        }
    }
}
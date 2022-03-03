package com.example.cryptohodl.view.main.table

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.view.main.dialogs.modifyDialog

@Composable
fun tableRow(
    asset: Asset,
    gain: Long,
    color: Color,
    currency: String,
    onDeleteClick: (Int) -> Unit,
    onRowClick: (String) -> Unit,
    showModify: String,
    onSaveClicked: (Asset) -> Unit,
) {
    Row(modifier = Modifier.clickable { asset.id?.let { onRowClick(asset.toString()) } }) {
        Text(asset.ticker)
        Text("${asset.invested}$currency")
        Text("${asset.value}$currency ")
        Text("$gain$currency", color = color)
        Button(onClick = {
            asset.id?.let { onDeleteClick(it) }
        }) {
            Text("DELETE")
        }
    }
    if (showModify.isNotBlank()) {
        val assetString = showModify.split(",")
        val id = assetString[0].toInt()
        val ticker = assetString[1].toString()
        val invested = assetString[2].toLong()
        val value = assetString[3].toLong()

        val newAsset = Asset(id, ticker, invested, value)

        modifyDialog(
            onDismissClicked = onRowClick,
            onSaveClicked = onSaveClicked,
            asset = newAsset,
            currency = currency
        )
    }
}
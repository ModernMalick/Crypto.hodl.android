package com.example.cryptohodl.view.main.table

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptohodl.model.Asset

@Composable
fun tableRow(
    asset: Asset,
    gain: Long,
    color: Color,
    currency: String,
    onDeleteClick: (Int) -> Unit,
    onRowClick: (Int) -> Unit,
) {
    Row(modifier = Modifier.clickable { asset.id?.let { onRowClick(it) } }) {
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
}

@Preview(showBackground = true)
@Composable
fun rowPreview() {
    val asset = Asset(1, "CRO", 100, 250)
    val del = fun(id: Int?) {
        Log.e("CLICKED", "DELETE $id")
    }
    tableRow(asset, asset.value - asset.invested, Color.Green, "$", del, del)
}
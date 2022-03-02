package com.example.cryptohodl.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.view.main.dialogs.addDialog
import com.example.cryptohodl.view.main.dialogs.settingsDialog
import com.example.cryptohodl.view.main.table.assetTable

@Composable
fun homeScreen(
    assets: List<Asset>,
    value: Long,
    invested: Long,
    gainsPercentage: Long,
    gainsFiat: Long,
    image: Int,
    currency: String,
    onDeleteClick: (Int) -> Unit,
    toggleAddDialog: () -> Unit,
    toggleSettingsDialog: () -> Unit,
    toggleModifyDialog: (Int) -> Unit,
    showAdd: Boolean,
    showSettings: Boolean,
    showModify: String,
    onSaveAdd: (Asset) -> Unit,
) {
    Column {
        header(value, invested, gainsPercentage, gainsFiat, image, currency)
        assetTable(assets, currency, onDeleteClick, toggleModifyDialog)
        Button(onClick = {
            toggleAddDialog()
        }) {
            Text("NEW")
        }
        Button(onClick = {
            toggleSettingsDialog()
        }) {
            Text("SETTINGS")
        }
    }
    if (showAdd) {
        addDialog(toggleAddDialog, onSaveAdd, currency)
    }
    if (showSettings) {
        settingsDialog(toggleSettingsDialog)
    }
//    if (showModify != "0" && showModify != "") {
//        modifyDialog(toggleModifyDialog, )
//    }
}
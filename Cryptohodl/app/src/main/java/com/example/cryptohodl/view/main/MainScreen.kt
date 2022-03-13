package com.example.cryptohodl.view.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cryptohodl.R
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.view.main.dialogs.addDialog
import com.example.cryptohodl.view.main.dialogs.settingsDialog
import com.example.cryptohodl.view.main.table.assetTable
import com.example.cryptohodl.view.ui.theme.CustomTheme


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
    toggleSettingsDialog: (String) -> Unit,
    toggleModifyDialog: (String) -> Unit,
    showAdd: Boolean,
    showSettings: Boolean,
    showModify: String,
    onSaveAdd: (Asset) -> Unit,
    onSaveModify: (Asset) -> Unit,
    rate: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        CustomTheme.colors.primary,
                        CustomTheme.colors.secondary,
                    ),
                    start = Offset.Zero,
                    end = Offset(Float.POSITIVE_INFINITY.times(0.5f), Float.POSITIVE_INFINITY.times(0.5f))
                )
            )
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .matchParentSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header(value, invested, gainsPercentage, gainsFiat, image, currency)
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .weight(1f)
            ) {
                assetTable(assets, currency, onDeleteClick, toggleModifyDialog, showModify, onSaveModify)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 36.dp),
            ) {
                FloatingActionButton(
                    onClick = { toggleAddDialog() },
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp)
                ) {
                    Image(painterResource(R.drawable.plus), "Ajouter un investissement")
                }
                FloatingActionButton(
                    onClick = { toggleSettingsDialog("") },
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(48.dp)
                ) {
                    Image(painterResource(R.drawable.settings), "Paramètres")
                }
            }
        }
        if (showAdd) {
            addDialog(toggleAddDialog, onSaveAdd, currency)
        }
        if (showSettings) {
            settingsDialog(toggleSettingsDialog, currency, rate)
        }
    }
}
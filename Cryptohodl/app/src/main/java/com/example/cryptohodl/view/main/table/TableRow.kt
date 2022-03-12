package com.example.cryptohodl.view.main.table

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.view.main.dialogs.modifyDialog
import com.example.cryptohodl.view.ui.theme.CustomTheme
import com.example.cryptohodl.view.ui.theme.rounded

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
    Row(modifier = Modifier
                    .clickable( onClick = { asset.id?.let { onRowClick(asset.toString()) } })
                    .background(color = CustomTheme.colors.surfaceB, rounded)
                    .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(asset.ticker, modifier = Modifier
            .weight(1f)
            , textAlign = TextAlign.Center
            , color = Color.White, style = CustomTheme.typography.medium
        )
        Text("${asset.invested}$currency", modifier = Modifier
            .weight(1f)
            , textAlign = TextAlign.Center
            , color = Color.White, style = CustomTheme.typography.medium
        )
        Text("${asset.value}$currency ", modifier = Modifier
            .weight(1f)
            , textAlign = TextAlign.Center
            , color = Color.White, style = CustomTheme.typography.medium
        )
        Text("$gain$currency", color = color, modifier = Modifier
            .weight(1f)
            , textAlign = TextAlign.Center
            , style = CustomTheme.typography.medium
        )
        Image(
            painterResource(com.example.cryptohodl.R.drawable.delete), "Supprimer un investissement",
            modifier = Modifier
                .padding(12.dp)
                .size(36.dp)
                .clickable { asset.id?.let { onDeleteClick(it) } }
        )
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
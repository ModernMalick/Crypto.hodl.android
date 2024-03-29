package com.example.cryptohodl.controller

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.cryptohodl.HodlApp.Companion.context
import com.example.cryptohodl.R
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.model.AssetDao
import com.example.cryptohodl.model.AssetDatabase
import com.example.cryptohodl.notifyObserver
import com.example.cryptohodl.view.main.dialogs.DialogViewModel
import com.example.cryptohodl.view.main.homeScreen
import com.example.cryptohodl.view.main.table.AssetViewModel
import com.example.cryptohodl.view.ui.theme.CryptohodlTheme

class MainActivity : ComponentActivity() {

    private lateinit var assetDatabase: AssetDatabase
    private lateinit var assetDao: AssetDao
    private lateinit var assetViewModel: AssetViewModel
    private lateinit var dialogViewModel: DialogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        assetDatabase = Room.databaseBuilder(
            applicationContext,
            AssetDatabase::class.java,
            AssetDatabase.DATABASE_NAME
        ).build()

        assetDao = assetDatabase.assetDao

        assetViewModel = ViewModelProvider(this)[AssetViewModel::class.java]
        dialogViewModel = ViewModelProvider(this)[DialogViewModel::class.java]

        dialogViewModel.getMerger().addSource(dialogViewModel.getShowAdd()) { showAdd ->
            if (showAdd) {
                dialogViewModel.getMerger().value = "add"
            } else {
                dialogViewModel.getMerger().value = ""
            }
        }
        dialogViewModel.getMerger().addSource(dialogViewModel.getShowSettings()) { showSettings ->
            if (showSettings) {
                dialogViewModel.getMerger().value = "settings"
            } else {
                dialogViewModel.getMerger().value = ""
            }
        }
        dialogViewModel.getMerger().addSource(dialogViewModel.getShowModify()) { showModify ->
            if (showModify.isNotBlank()) {
                dialogViewModel.getMerger().value = showModify
            } else {
                dialogViewModel.getMerger().value = ""
            }
        }

        assetViewModel.getAssetList()
        assetViewModel.getAssetList().observe(this, Observer { assets ->

            var value: Long = 0
            var invested: Long = 0

            assets.forEach { asset ->
                value += asset.value
                invested += asset.invested
            }

            val gainsFiat: Long = value - invested

            var gainsPercentage: Long = if (invested != 0.toLong()) {
                ((value - invested) * 100) / invested
            } else {
                100
            }

            if ((value == 0.toLong()) && (invested == 0.toLong())) {
                gainsPercentage = 0
            }

            var statusImage = R.drawable.bankrupt
            if (gainsFiat > 0) {
                statusImage = R.drawable.rocket
            } else if (gainsFiat < 0) {
                statusImage = R.drawable.explosion
            }

            dialogViewModel.getMerger().observe(this, Observer { result ->
                val showAdd = result.equals("add")
                val showSettings = result.equals("settings")
                var showModify = ""

                if (!showAdd && !showSettings && result != "") {
                    showModify = result
                }

                val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
                val currency = sharedPreferences.getString("currency", "€").toString()
                val intro = sharedPreferences.getBoolean("intro", true)

                if (intro && assets.size > 0) {
                    Toast.makeText(
                        context,
                        "Appuyez sur une ligne du tableau pour la modifier",
                        Toast.LENGTH_LONG
                    ).show()
                    sharedPreferences.edit().putBoolean("intro", false).apply()
                }

                WindowCompat.setDecorFitsSystemWindows(window, false)
                setContent {
                    CryptohodlTheme {
                        homeScreen(
                            assets,
                            value,
                            invested,
                            gainsPercentage,
                            gainsFiat,
                            statusImage,
                            currency,
                            deleteAsset,
                            toggleAddDialog,
                            toggleSettingsDialog,
                            toggleModifyDialog,
                            showAdd,
                            showSettings,
                            showModify,
                            addAsset,
                            modifyAsset,
                            rate
                        )
                    }
                }
            })
        })
        buildAssetList()
    }

    private fun buildAssetList() {
        assetViewModel.clearList()
        val thread = Thread {
            try {
                if (assetDao.getAssets().isNotEmpty()) {
                    assetDao.getAssets().forEach { asset ->
                        runOnUiThread {
                            assetViewModel.addToCurrentList(asset)
                        }
                    }
                } else {
                    runOnUiThread {
                        assetViewModel.getAssetList().notifyObserver()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private val addAsset = fun(asset: Asset) {
        val thread = Thread {
            try {
                assetDao.insertAssets(asset)
                runOnUiThread {
                    buildAssetList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private val deleteAsset = fun(id: Int) {
        Log.e("DELETE", id.toString())
        val thread = Thread {
            try {
                assetDao.deleteAsset(id)
                runOnUiThread {
                    buildAssetList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private val modifyAsset = fun(asset: Asset) {
        Log.e("MODIFY", asset.id.toString())
        val thread = Thread {
            try {
                asset.id?.let {
                    assetDao.updateAsset(asset.ticker, asset.invested, asset.value, it)
                }
                runOnUiThread {
                    buildAssetList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private val toggleAddDialog = fun() {
        dialogViewModel.toggleShowAdd()
    }

    private val toggleSettingsDialog = fun(newCurrency: String) {
        if (newCurrency.isNotBlank()) {
            val sharedPref = getPreferences(Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("currency", newCurrency)
                apply()
            }
        }
        dialogViewModel.toggleShowSettings()
    }

    private val toggleModifyDialog = fun(string: String) {
        dialogViewModel.toggleShowModify(string)
    }

    private val rate = fun() {
        Log.e("Rate", "PRESSED")
    }
}
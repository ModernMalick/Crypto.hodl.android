package com.example.cryptohodl.controller

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.cryptohodl.R
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.model.AssetDao
import com.example.cryptohodl.model.AssetDatabase
import com.example.cryptohodl.notifyObserver
import com.example.cryptohodl.view.main.dialogs.DialogViewModel
import com.example.cryptohodl.view.main.homeScreen
import com.example.cryptohodl.view.main.table.AssetViewModel

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
            var gainsPercentage: Long = 0
            var gainsFiat: Long = 0

            assets.forEach { asset ->
                value += asset.value
                invested += asset.invested
            }

            gainsFiat = value - invested

            gainsPercentage = if (invested != 0.toLong()) {
                ((value - invested) * 100) / invested
            } else {
                100
            }

            dialogViewModel.getMerger().observe(this, Observer { result ->
                val showAdd = result.equals("add")
                val showSettings = result.equals("settings")
                var showModify = ""

                if (!showAdd && !showSettings && result != "") {
                    showModify = result
                }

                val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
                val currency = sharedPreferences.getString("currency", "$").toString()

                setContent {
                    homeScreen(
                        assets,
                        value,
                        invested,
                        gainsPercentage,
                        gainsFiat,
                        R.drawable.placeholder,
                        currency,
                        deleteAsset,
                        toggleAddDialog,
                        toggleSettingsDialog,
                        toggleModifyDialog,
                        showAdd,
                        showSettings,
                        showModify,
                        addAsset,
                        modifyAsset
                    )
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
}
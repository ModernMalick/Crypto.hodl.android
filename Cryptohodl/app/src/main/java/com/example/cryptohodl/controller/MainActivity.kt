package com.example.cryptohodl.controller

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.cryptohodl.R
import com.example.cryptohodl.model.AssetDao
import com.example.cryptohodl.model.AssetDatabase
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

            if (invested != 0.toLong()) {
                gainsPercentage = ((value - invested) * 100) / invested
                gainsFiat = value - invested
            }

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
                if (showModify != 0) {
                    dialogViewModel.getMerger().value = showModify.toString()
                } else {
                    dialogViewModel.getMerger().value = ""
                }
            }
            dialogViewModel.getMerger().observe(this, Observer { result ->
                val showAdd = result.equals("add")
                val showSettings = result.equals("settings")
                var showModify = ""

                if (!showAdd && !showSettings) {
                    showModify = result
                }

                setContent {
                    homeScreen(
                        assets,
                        value,
                        invested,
                        gainsPercentage,
                        gainsFiat,
                        R.drawable.placeholder,
                        "$",
                        deleteAsset,
                        toggleAddDialog,
                        toggleSettingsDialog,
                        toggleModifyDialog,
                        showAdd,
                        showSettings,
                        showModify
                    )
                }
            })
        })

        buildAssetList()
    }

    private fun buildAssetList() {
        val thread = Thread {
            try {
                assetDao.getAssets().forEach { asset ->
                    assetViewModel.addToCurrentList(asset)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private val deleteAsset = fun(id: Int?) {
        Log.e("CLICKED", "DELETE $id")
    }

    private val toggleAddDialog = fun() {
        dialogViewModel.toggleShowAdd()
    }
    private val toggleSettingsDialog = fun() {
        dialogViewModel.toggleShowSettings()
    }
    private val toggleModifyDialog = fun(id: Int) {
        dialogViewModel.toggleShowModify(id)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//
//}

//            it.forEach { asset ->
//                Log.e("ID : " + asset.id, "TICKER : " + asset.ticker + " INVESTED : " + asset.invested + " VALUE : " + asset.value)
//            }

//                val asset = Asset(ticker = "eth", value = 15, invested = 5)
//                assetDao.insertAssets(asset)
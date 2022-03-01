package com.example.cryptohodl.controller

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.cryptohodl.R
import com.example.cryptohodl.model.AssetDao
import com.example.cryptohodl.model.AssetDatabase
import com.example.cryptohodl.view.main.dialogs.modifyDialog
import com.example.cryptohodl.view.main.homeScreen
import com.example.cryptohodl.view.main.table.AssetViewModel


class MainActivity : ComponentActivity() {

    private lateinit var assetDatabase: AssetDatabase
    private lateinit var assetDao: AssetDao
    private lateinit var assetViewModel: AssetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        assetDatabase = Room.databaseBuilder(
            applicationContext,
            AssetDatabase::class.java,
            AssetDatabase.DATABASE_NAME
        ).build()

        assetDao = assetDatabase.assetDao

        assetViewModel = ViewModelProvider(this)[AssetViewModel::class.java]

        assetViewModel.getAssetList()

        assetViewModel.getAssetList().observe(this, Observer { assets ->
//            it.forEach { asset ->
//                Log.e("ID : " + asset.id, "TICKER : " + asset.ticker + " INVESTED : " + asset.invested + " VALUE : " + asset.value)
//            }
            var value: Long = 0
            var invested: Long = 0
            var gainsPercentage: Long = 0
            var gainsFiat: Long = 0

            assets.forEach { asset ->
                value += asset.value
                invested += asset.invested
            }

            if(invested != 0.toLong()) {
                gainsPercentage = ((value - invested) * 100) / invested
                gainsFiat = value - invested
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
                    addAssetClick,
                    settingsClick,
                    rowClick
                )
            }
        })

        buildAssetList()
    }

    private fun buildAssetList() {
        val thread = Thread {
            try {
//                val asset = Asset(ticker = "eth", value = 15, invested = 5)
//                assetDao.insertAssets(asset)
                assetDao.getAssets().forEach{ asset ->
                    assetViewModel.addToCurrentList(asset)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private val addAssetClick = fun(){
        Log.e("CLICKED", "ADD")
    }

    private val rowClick = fun(id: Int?){
        Log.e("CLICKED", "ROW $id")
    }

    private val deleteAsset = fun(id: Int?){
        Log.e("CLICKED", "DELETE $id")
    }

    private val settingsClick = fun(){
        Log.e("CLICKED", "SETTINGS")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}
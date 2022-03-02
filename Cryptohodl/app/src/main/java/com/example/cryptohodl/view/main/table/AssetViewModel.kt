package com.example.cryptohodl.view.main.table

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptohodl.model.Asset
import com.example.cryptohodl.notifyObserver

class AssetViewModel : ViewModel() {
    private var assets: MutableLiveData<ArrayList<Asset>>? = null

    fun clearList() {
        assets?.value?.clear()
    }

    fun getAssetList(): MutableLiveData<ArrayList<Asset>> {
        if (assets == null) {
            assets = MutableLiveData<ArrayList<Asset>>()
            assets!!.value = ArrayList()
        }
        return assets!!
    }

    fun addToCurrentList(asset: Asset) {
        assets?.value?.add(asset)
        assets?.notifyObserver()
    }

    fun removeFromCurrentList(asset: Asset) {
        assets?.value?.remove(asset)
    }
}
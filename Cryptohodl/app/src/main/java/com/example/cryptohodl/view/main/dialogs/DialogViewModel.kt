package com.example.cryptohodl.view.main.dialogs

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {
    private var showAdd: MutableLiveData<Boolean>? = null
    private var showSettings: MutableLiveData<Boolean>? = null
    private var liveDataMerger: MediatorLiveData<String>? = null

    fun getShowAdd(): MutableLiveData<Boolean> {
        if (showAdd == null) {
            showAdd = MutableLiveData<Boolean>()
            showAdd!!.value = false
        }
        return showAdd!!
    }

    fun toggleShowAdd() {
        val status = getShowAdd()
        showAdd!!.value = !status.value!!
    }

    fun getShowSettings(): MutableLiveData<Boolean> {
        if (showSettings == null) {
            showSettings = MutableLiveData<Boolean>()
            showSettings!!.value = false
        }
        return showSettings!!
    }

    fun toggleShowSettings() {
        val status = getShowSettings()
        showSettings!!.value = !status.value!!
    }

    fun getMerger(): MediatorLiveData<String> {
        if (liveDataMerger == null) {
            liveDataMerger = MediatorLiveData<String>()
            liveDataMerger!!.value = ""
        }
        return liveDataMerger!!
    }
}
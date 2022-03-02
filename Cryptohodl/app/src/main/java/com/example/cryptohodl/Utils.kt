package com.example.cryptohodl

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

fun toaster(context: Context?, string: String?) {
    val toast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
    toast.show()
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}
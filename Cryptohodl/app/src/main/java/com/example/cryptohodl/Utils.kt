package com.example.cryptohodl

import android.content.Context
import android.widget.Toast

fun toaster(context: Context?, string: String?) {
    val toast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
    toast.show()
}
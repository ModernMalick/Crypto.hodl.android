package com.example.cryptohodl.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Asset(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "ticker")
    val ticker: String,

    @ColumnInfo(name = "invested")
    val invested: Long,

    @ColumnInfo(name = "value")
    val value: Long
)

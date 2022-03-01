package com.example.cryptohodl.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Asset::class],
    version = 1
)

abstract class AssetDatabase : RoomDatabase() {
    abstract val assetDao: AssetDao

    companion object {
        const val DATABASE_NAME = "asset_db"
    }
}
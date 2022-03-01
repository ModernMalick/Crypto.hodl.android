package com.example.cryptohodl.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AssetDao {
    @Query("SELECT * FROM asset")
    fun getAssets(): List<Asset>

    @Query("SELECT * FROM asset WHERE id IN (:assetID)")
    fun getAsset(assetID: Int): Asset

    @Insert
    fun insertAssets(vararg assets: Asset?)

    @Delete
    fun deleteAsset(asset: Asset)

    @Query("UPDATE asset SET ticker = :ticker, invested = :invested, value = :value WHERE id = :id")
    fun updateAsset(ticker: String?, invested: Long?, value: Long, id: Int)
}
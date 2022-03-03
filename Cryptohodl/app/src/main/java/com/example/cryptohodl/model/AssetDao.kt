package com.example.cryptohodl.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AssetDao {
    @Query("SELECT * FROM asset ORDER BY ticker ASC")
    fun getAssets(): List<Asset>

    @Query("SELECT * FROM asset WHERE id IN (:assetID)")
    fun getAsset(assetID: Int): Asset

    @Insert
    fun insertAssets(vararg assets: Asset?)

    @Query("DELETE FROM asset WHERE id IN (:assetID)")
    fun deleteAsset(assetID: Int)

    @Query("UPDATE asset SET ticker = :ticker, invested = :invested, value = :value WHERE id IN (:id)")
    fun updateAsset(ticker: String?, invested: Long?, value: Long, id: Int)
}
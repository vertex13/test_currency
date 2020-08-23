package com.github.vertex13.testcurrency.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyPairDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pair: CurrencyPairEntity)

    @Query("SELECT * FROM currency_pair WHERE isSubscribed = 1")
    suspend fun getSubscribed(): List<CurrencyPairEntity>

}

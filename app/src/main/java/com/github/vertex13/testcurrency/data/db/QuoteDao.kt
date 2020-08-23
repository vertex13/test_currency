package com.github.vertex13.testcurrency.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuoteDao {

    @Insert
    suspend fun insertAll(quotes: List<QuoteEntity>)

    @Query("SELECT * FROM quote WHERE currencyPair = :currencyPair")
    suspend fun getForCurrencyPair(currencyPair: String): List<QuoteEntity>

}

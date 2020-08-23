package com.github.vertex13.testcurrency.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
class QuoteEntity(
    val currencyPair: String,
    val bid: Double,
    val ask: Double,
    val spread: Double,
    val timestamp: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

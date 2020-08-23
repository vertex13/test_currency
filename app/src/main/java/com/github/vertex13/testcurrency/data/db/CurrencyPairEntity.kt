package com.github.vertex13.testcurrency.data.db

import androidx.room.Entity

@Entity(tableName = "currency_pair", primaryKeys = ["first", "second"])
class CurrencyPairEntity(
    val first: String,
    val second: String,
    val isSubscribed: Int
)

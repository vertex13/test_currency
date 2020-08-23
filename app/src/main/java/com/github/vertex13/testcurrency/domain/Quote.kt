package com.github.vertex13.testcurrency.domain

import java.util.*

data class Quote(
    val currencyPair: CurrencyPair,
    val bid: Double,
    val ask: Double,
    val spread: Double,
    val timestamp: Date
)

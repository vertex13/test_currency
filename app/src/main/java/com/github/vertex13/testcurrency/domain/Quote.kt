package com.github.vertex13.testcurrency.domain

data class Quote(
    val currencyPair: CurrencyPair,
    val bid: Double,
    val ask: Double,
    val spread: Double
)

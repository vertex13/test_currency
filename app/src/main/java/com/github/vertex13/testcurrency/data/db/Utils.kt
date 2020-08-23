package com.github.vertex13.testcurrency.data.db

import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.Quote
import java.util.*

fun CurrencyPair.toEntity(isSubscribed: Boolean): CurrencyPairEntity = CurrencyPairEntity(
    first,
    second,
    if (isSubscribed) 1 else 0
)

fun CurrencyPairEntity.fromEntity(): CurrencyPair = CurrencyPair(first, second)

fun Quote.toEntity(): QuoteEntity = QuoteEntity(
    currencyPair.toQuoteTypeString(),
    bid,
    ask,
    spread,
    timestamp.time
)

fun QuoteEntity.fromEntity(): Quote = Quote(
    currencyPair.fromQuoteCurrencyPair(),
    bid,
    ask,
    spread,
    Date(timestamp)
)

private fun CurrencyPair.toQuoteTypeString(): String = first + second

private fun String.fromQuoteCurrencyPair(): CurrencyPair {
    val first = substring(0, 3)
    val second = substring(3, 6)
    return CurrencyPair(first, second)
}

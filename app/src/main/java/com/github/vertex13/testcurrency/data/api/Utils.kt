package com.github.vertex13.testcurrency.data.api

import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.Quote
import java.util.*

fun CurrencyPair.toDto(): CurrencyPairDto = first + second

fun CurrencyPairDto.fromDto(): CurrencyPair {
    val first = substring(0, 3)
    val second = substring(3, 6)
    return CurrencyPair(first, second)
}

fun QuoteDto.fromDto(timestamp: Date): Quote = Quote(
    s.fromDto(), b, a, spr, timestamp
)

package com.github.vertex13.testcurrency.data.api

class QuotesDto(
    val ticks: List<QuoteDto>
)

class QuoteDto(
    val s: CurrencyPairDto,
    val b: Double,
    val bf: Int,
    val a: Double,
    val af: Int,
    val spr: Double
)

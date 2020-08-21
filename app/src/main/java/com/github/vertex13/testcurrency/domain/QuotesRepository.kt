package com.github.vertex13.testcurrency.domain

interface QuotesRepository {

    suspend fun getAll(currencyPair: CurrencyPair): List<Quote>

}

package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.Quote
import com.github.vertex13.testcurrency.domain.QuotesRepository

class LocalQuotesRepository: QuotesRepository {

    override suspend fun getAll(currencyPair: CurrencyPair): List<Quote> {
        return emptyList()
    }

}

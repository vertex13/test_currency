package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.data.db.AppDatabase
import com.github.vertex13.testcurrency.data.db.QuoteEntity
import com.github.vertex13.testcurrency.data.db.fromEntity
import com.github.vertex13.testcurrency.data.db.toQuoteEntityType
import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.Quote
import com.github.vertex13.testcurrency.domain.QuotesRepository

class LocalQuotesRepository(
    private val db: AppDatabase
) : QuotesRepository {

    override suspend fun getAll(currencyPair: CurrencyPair): List<Quote> {
        return db.quoteDao()
            .getForCurrencyPair(currencyPair.toQuoteEntityType())
            .map(QuoteEntity::fromEntity)
    }

}

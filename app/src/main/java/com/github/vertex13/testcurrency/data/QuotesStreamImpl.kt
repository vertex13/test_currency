package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.data.api.QuotesDto
import com.github.vertex13.testcurrency.data.api.QuotesEccallsApi
import com.github.vertex13.testcurrency.data.api.fromDto
import com.github.vertex13.testcurrency.data.api.toDto
import com.github.vertex13.testcurrency.data.db.AppDatabase
import com.github.vertex13.testcurrency.data.db.toEntity
import com.github.vertex13.testcurrency.domain.Quote
import com.github.vertex13.testcurrency.domain.QuotesStream
import com.github.vertex13.testcurrency.domain.QuotesStreamListener
import com.github.vertex13.testcurrency.usecases.GetSubscribedCurrencyPairs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class QuotesStreamImpl(
    private val api: QuotesEccallsApi,
    private val db: AppDatabase,
    private val getSubscribedCurrencyPairs: GetSubscribedCurrencyPairs
) : QuotesStream {

    private val listeners = hashSetOf<QuotesStreamListener>()

    override fun addListener(listener: QuotesStreamListener) {
        if (listeners.isEmpty()) {
            api.addQuotesListener(::onQuotesReceived)
            subscribeToCurrencyPairs()
        }
        listeners.add(listener)
    }

    override fun removeListener(listener: QuotesStreamListener) {
        listeners.remove(listener)
        if (listeners.isEmpty()) {
            api.removeQuotesListener(::onQuotesReceived)
        }
    }

    private fun onQuotesReceived(quotesDto: QuotesDto) {
        val now = Date()
        val quotes = quotesDto.ticks.map { it.fromDto(now) }
        notifyListeners(quotes)

        GlobalScope.launch {
            db.quoteDao().insertAll(quotes.map(Quote::toEntity))
        }
    }

    private fun notifyListeners(quotes: List<Quote>) {
        listeners.forEach { it(quotes) }
    }

    private fun subscribeToCurrencyPairs() {
        GlobalScope.launch {
            val pairs = getSubscribedCurrencyPairs()
            pairs.forEach {
                api.subscribe(it.toDto())
            }
        }
    }

}

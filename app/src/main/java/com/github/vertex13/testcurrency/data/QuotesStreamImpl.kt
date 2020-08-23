package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.data.api.CurrencyPairDto
import com.github.vertex13.testcurrency.data.api.QuotesDto
import com.github.vertex13.testcurrency.data.api.QuotesEccallsApi
import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.Quote
import com.github.vertex13.testcurrency.domain.QuotesStream
import com.github.vertex13.testcurrency.domain.QuotesStreamListener

class QuotesStreamImpl(
    private val api: QuotesEccallsApi
) : QuotesStream {

    private val listeners = hashSetOf<QuotesStreamListener>()

    override fun addListener(listener: QuotesStreamListener) {
        if (listeners.isEmpty()) {
            api.addQuotesListener(::onQuotesReceived)
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
        val quotes = quotesDto.ticks.map {
            Quote(it.s.toCurrencyPair(), it.b, it.a, it.spr)
        }
        notifyListeners(quotes)
    }

    private fun notifyListeners(quotes: List<Quote>) {
        listeners.forEach { it(quotes) }
    }

    private fun CurrencyPairDto.toCurrencyPair(): CurrencyPair {
        val first = substring(0, 3)
        val second = substring(3, 6)
        return CurrencyPair(first, second)
    }

}

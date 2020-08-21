package com.github.vertex13.testcurrency.usecases

import com.github.vertex13.testcurrency.domain.*

class SubscribeToCurrencyPair(
    private val subscriptionManager: CurrencyPairSubscriptionManager
) {
    suspend operator fun invoke(currencyPair: CurrencyPair) {
        subscriptionManager.subscribe(currencyPair)
    }
}

class UnsubscribeFromCurrencyPair(
    private val subscriptionManager: CurrencyPairSubscriptionManager
) {
    suspend operator fun invoke(currencyPair: CurrencyPair) {
        subscriptionManager.unsubscribe(currencyPair)
    }
}

class SubscribeToQuotesStream(
    private val stream: QuotesStream
) {
    suspend operator fun invoke(listener: QuotesStreamListener) {
        stream.subscribe(listener)
    }
}

class UnsubscribeFromQuotesStream(
    private val stream: QuotesStream
) {
    suspend operator fun invoke(listener: QuotesStreamListener) {
        stream.unsubscribe(listener)
    }
}

class GetQuotesHistory(
    private val quotesRepository: QuotesRepository
) {
    suspend operator fun invoke(currencyPair: CurrencyPair): List<Quote> {
        return quotesRepository.getAll(currencyPair)
    }
}

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

class GetSubscribedCurrencyPairs(
    private val subscriptionManager: CurrencyPairSubscriptionManager
) {
    suspend operator fun invoke(): List<CurrencyPair> {
        return subscriptionManager.getSubscribedCurrencyPairs()
    }
}

class TrackSubscribedCurrencyPairs(
    private val subscriptionManager: CurrencyPairSubscriptionManager
) {
    operator fun invoke(listener: SubscribedPairsListener) {
        subscriptionManager.addSubscribedPairsListener(listener)
    }
}

class UntrackSubscribedCurrencyPairs(
    private val subscriptionManager: CurrencyPairSubscriptionManager
) {
    operator fun invoke(listener: SubscribedPairsListener) {
        subscriptionManager.removeSubscribedPairsListener(listener)
    }
}

class ListenQuotes(
    private val stream: QuotesStream
) {
    operator fun invoke(listener: QuotesStreamListener) {
        stream.addListener(listener)
    }
}

class CancelListeningQuotes(
    private val stream: QuotesStream
) {
    operator fun invoke(listener: QuotesStreamListener) {
        stream.removeListener(listener)
    }
}

class GetQuotesHistory(
    private val quotesRepository: QuotesRepository
) {
    suspend operator fun invoke(currencyPair: CurrencyPair): List<Quote> {
        return quotesRepository.getAll(currencyPair)
    }
}

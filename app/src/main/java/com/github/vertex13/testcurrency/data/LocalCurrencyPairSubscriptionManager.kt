package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.data.api.CurrencyPairDto
import com.github.vertex13.testcurrency.data.api.QuotesEccallsApi
import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.CurrencyPairSubscriptionManager
import com.github.vertex13.testcurrency.domain.SubscribedPairsListener

class LocalCurrencyPairSubscriptionManager(
    private val api: QuotesEccallsApi
) : CurrencyPairSubscriptionManager {

    private val subscribedPairs =
        hashSetOf<CurrencyPair>(CurrencyPair("USD", "EUR"), CurrencyPair("BTC", "USD"))

    private val listeners = hashSetOf<SubscribedPairsListener>()

    override suspend fun subscribe(currencyPair: CurrencyPair) {
        api.subscribe(currencyPair.toDto())
        subscribedPairs.add(currencyPair)
    }

    override suspend fun unsubscribe(currencyPair: CurrencyPair) {
        api.unsubscribe(currencyPair.toDto())
        subscribedPairs.remove(currencyPair)
    }

    override suspend fun getSubscribedCurrencyPairs(): Collection<CurrencyPair> {
        return subscribedPairs
    }

    override fun addSubscribedPairsListener(listener: SubscribedPairsListener) {
        listeners.add(listener)
    }

    override fun removeSubscribedPairsListener(listener: SubscribedPairsListener) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        listeners.forEach { it(subscribedPairs) }
    }

    private fun CurrencyPair.toDto(): CurrencyPairDto {
        return first + second
    }

}

package com.github.vertex13.testcurrency.domain

interface CurrencyPairSubscriptionManager {

    suspend fun subscribe(currencyPair: CurrencyPair)

    suspend fun unsubscribe(currencyPair: CurrencyPair)

    suspend fun getSubscribedCurrencyPairs(): Collection<CurrencyPair>

    fun addSubscribedPairsListener(listener: SubscribedPairsListener)

    fun removeSubscribedPairsListener(listener: SubscribedPairsListener)

}

typealias SubscribedPairsListener = (Collection<CurrencyPair>) -> Unit

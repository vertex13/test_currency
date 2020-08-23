package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.data.api.QuotesEccallsApi
import com.github.vertex13.testcurrency.data.api.toDto
import com.github.vertex13.testcurrency.data.db.AppDatabase
import com.github.vertex13.testcurrency.data.db.CurrencyPairEntity
import com.github.vertex13.testcurrency.data.db.fromEntity
import com.github.vertex13.testcurrency.data.db.toEntity
import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.CurrencyPairSubscriptionManager
import com.github.vertex13.testcurrency.domain.SubscribedPairsListener
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LocalCurrencyPairSubscriptionManager(
    private val api: QuotesEccallsApi,
    private val db: AppDatabase
) : CurrencyPairSubscriptionManager {

    private val subscribedPairs = hashSetOf<CurrencyPair>()

    private val listeners = hashSetOf<SubscribedPairsListener>()

    private val init: Deferred<Any> = GlobalScope.async(start = CoroutineStart.LAZY) {
        val pairs = db.currencyPairDao().getSubscribed().map(CurrencyPairEntity::fromEntity)
        subscribedPairs.addAll(pairs)
    }

    init {
        init.start()
    }

    override suspend fun subscribe(currencyPair: CurrencyPair) {
        init.await()

        api.subscribe(currencyPair.toDto())
        subscribedPairs.add(currencyPair)
        notifyListeners()

        db.currencyPairDao().insert(currencyPair.toEntity(true))
    }

    override suspend fun unsubscribe(currencyPair: CurrencyPair) {
        init.await()

        api.unsubscribe(currencyPair.toDto())
        subscribedPairs.remove(currencyPair)
        notifyListeners()

        db.currencyPairDao().insert(currencyPair.toEntity(false))
    }

    override suspend fun getSubscribedCurrencyPairs(): Collection<CurrencyPair> {
        init.await()
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

}

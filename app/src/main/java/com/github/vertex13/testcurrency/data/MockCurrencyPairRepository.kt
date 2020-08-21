package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.CurrencyPairRepository

class MockCurrencyPairRepository : CurrencyPairRepository {

    private val pairs: List<CurrencyPair> = createPairs()

    override suspend fun getAll(): List<CurrencyPair> = pairs

    private fun createPairs(): List<CurrencyPair> {
        return listOf("BTCUSD", "EURUSD", "EURGBP", "USDJPY", "GBPUSD", "USDCHF", "USDCAD")
            .asSequence()
            .map(::pairFromString)
            .toList()
    }

    private fun pairFromString(str: String): CurrencyPair {
        val first = str.substring(0, 3)
        val second = str.substring(3, 6)
        return CurrencyPair(first, second)
    }

}

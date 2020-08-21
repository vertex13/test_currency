package com.github.vertex13.testcurrency.domain

interface CurrencyPairRepository {

    suspend fun getAll(): List<CurrencyPair>

}

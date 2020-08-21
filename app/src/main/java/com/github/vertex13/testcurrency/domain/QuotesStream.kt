package com.github.vertex13.testcurrency.domain

interface QuotesStream {

    suspend fun subscribe(listener: QuotesStreamListener)

    suspend fun unsubscribe(listener: QuotesStreamListener)

}

typealias QuotesStreamListener = (Quote) -> Unit

package com.github.vertex13.testcurrency.domain

interface QuotesStream {

    fun addListener(listener: QuotesStreamListener)

    fun removeListener(listener: QuotesStreamListener)

}

typealias QuotesStreamListener = (Quote) -> Unit

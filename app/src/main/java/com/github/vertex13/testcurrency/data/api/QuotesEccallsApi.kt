package com.github.vertex13.testcurrency.data.api

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.IOException

class QuotesEccallsApi {

    private var wsController: WebSocketController? = null

    private val listeners = hashSetOf<QuotesListener>()

    private val quotesJsonAdapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(QuotesDto::class.java)

    fun addQuotesListener(listener: QuotesListener) {
        listeners.add(listener)
        if (wsController == null) {
            wsController = WebSocketController(::handleMessage)
            wsController?.start()
        }
    }

    fun removeQuotesListener(listener: QuotesListener) {
        listeners.remove(listener)
        if (listeners.isEmpty()) {
            wsController?.close()
            wsController = null
        }
    }

    fun subscribe(currencyPair: CurrencyPairDto) {
        wsController?.sendMessage("SUBSCRIBE: $currencyPair")
    }

    fun unsubscribe(currencyPair: CurrencyPairDto) {
        wsController?.sendMessage("UNSUBSCRIBE $currencyPair")
    }

    private fun handleMessage(message: String) {
        val quotes = try {
            quotesJsonAdapter.fromJson(message)
        } catch (e: JsonDataException) {
            return
        }
        if (quotes != null) {
            listeners.forEach { it(quotes) }
        }
    }

}

typealias QuotesListener = (QuotesDto) -> Unit

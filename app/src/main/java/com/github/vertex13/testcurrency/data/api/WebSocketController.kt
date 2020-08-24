package com.github.vertex13.testcurrency.data.api

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

private const val URI = "wss://quotes.eccalls.mobi:18400"

private const val CLOSE_NORMAL_STATUS = 1000
private const val CLOSE_GOING_AWAY_STATUS = 1001

class WebSocketController(
    private val onMessageReceived: (String) -> Unit
) {

    private var webSocket: WebSocket? = null
    private val listener = Listener()
    private var isClosed = false
    private val messageQueue: Queue<String> = LinkedList()

    /**
     * throws [IllegalStateException] if the controller has been closed.
     */
    @Synchronized
    fun start() {
        if (isClosed) {
            throw IllegalStateException("The controller has been closed.")
        }
        if (webSocket == null) {
            webSocket = createWebSocket()
        }
    }

    @Synchronized
    fun close() {
        isClosed = true
        webSocket?.close(CLOSE_GOING_AWAY_STATUS, null)
        webSocket = null
        messageQueue.clear()
    }

    fun sendMessage(message: String) {
        messageQueue.offer(message)
        webSocket?.send(message)
    }

    private fun restart() {
        GlobalScope.launch {
            delay(5000)
            if (!isClosed) {
                webSocket?.close(CLOSE_NORMAL_STATUS, null)
                webSocket = null
                start()
            }
        }
    }

    private fun createWebSocket(): WebSocket {
        val trustManagers = arrayOf(EmptyTrustManager())
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagers, null)

        val request = Request.Builder().url(URI).build()

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManagers[0])
            .build()
            .newWebSocket(request, listener)
    }

    private fun resendMessages() {
        messageQueue.forEach { message ->
            webSocket?.send(message)
        }
    }

    inner class Listener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            resendMessages()
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            onMessageReceived(text)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(CLOSE_NORMAL_STATUS, null)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            if (code != CLOSE_GOING_AWAY_STATUS) {
                restart()
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("WebSocketController", "Listener.onFailure()", t)
            restart()
        }

    }

}

private class EmptyTrustManager : X509TrustManager {
    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) = Unit

    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) = Unit

    override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
}

package com.github.vertex13.testcurrency.usecases

import org.koin.dsl.module

val useCasesModule = module {
    factory { SubscribeToCurrencyPair(get()) }
    factory { UnsubscribeFromCurrencyPair(get()) }
    factory { GetSubscribedCurrencyPairs(get()) }
    factory { TrackSubscribedCurrencyPairs(get()) }
    factory { UntrackSubscribedCurrencyPairs(get()) }
    factory { GetAllCurrencyPairs(get()) }
    factory { ListenQuotes(get()) }
    factory { CancelListeningQuotes(get()) }
    factory { GetQuotesHistory(get()) }
}

package com.github.vertex13.testcurrency

import android.app.Application
import com.github.vertex13.testcurrency.presentation.presentationModule
import com.github.vertex13.testcurrency.usecases.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                presentationModule,
                useCasesModule
            )
        }
    }

}

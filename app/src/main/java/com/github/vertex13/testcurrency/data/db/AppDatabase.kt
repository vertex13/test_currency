package com.github.vertex13.testcurrency.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrencyPairEntity::class, QuoteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun build(appContext: Context): AppDatabase {
            return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "app-database.db"
            ).build()
        }
    }

    abstract fun currencyPairDao(): CurrencyPairDao

    abstract fun quoteDao(): QuoteDao

}

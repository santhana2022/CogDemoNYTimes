package com.cognizant.caponeteambuild.repository.local

import android.content.Context
import androidx.room.*
import com.cognizant.caponeteambuild.data.Result
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Database(entities = [Result::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                )
                    .addTypeConverter(Converters())
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
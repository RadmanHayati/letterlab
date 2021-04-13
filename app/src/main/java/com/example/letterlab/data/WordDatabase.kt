package com.example.letterlab.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.letterlab.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    class Callback @Inject constructor(
        private val database: Provider<WordDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().wordDao()
            applicationScope.launch {
                dao.insert(Word("Hi", "hello"))
                dao.insert(Word("Im", "me"))
                dao.insert(Word("glad", "happy", important = true))
                dao.insert(Word("you", "user"))
                dao.insert(Word("here", "using app", learned = true))
            }

        }
    }
}
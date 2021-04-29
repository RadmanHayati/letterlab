package com.example.letterlab.di

import android.app.Application
import androidx.room.Room
import com.example.letterlab.data.WordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application, callback: WordDatabase.Callback
    ) = Room.databaseBuilder(app, WordDatabase::class.java, "word_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()


    @Provides
    fun provideWordDao(db: WordDatabase) = db.wordDao()

    //we inject the coroutine in order to avoid making this each time
    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
    //SupervisorJob when the childes of a coroutine get cancelled or finished
    //the parent also will be canceled but with a supervisor job they work separately
}
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
// we made a costume annotation here
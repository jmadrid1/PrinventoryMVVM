package com.example.prinventory_mvvm.di

import android.content.Context
import androidx.room.Room
import com.example.prinventory_mvvm.data.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
            @ApplicationContext app: Context) = Room.databaseBuilder(
            app,
            Database::class.java,
            "prinventory_db")
            .build()

    @Singleton
    @Provides
    fun providePrinterDao(db: Database) = db.printerDao()

    @Singleton
    @Provides
    fun provideTonerDao(db: Database) = db.tonerDao()

    @Singleton
    @Provides
    fun provideVendorDao(db: Database) = db.vendorDao()

}
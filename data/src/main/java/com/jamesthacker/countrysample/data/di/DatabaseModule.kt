package com.jamesthacker.countrysample.data.di

import android.content.Context
import androidx.room.Room
import com.jamesthacker.countrysample.data.db.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCountryDatabase(
        @ApplicationContext context: Context,
    ): CountryDatabase {
        return Room.databaseBuilder(
            context,
            CountryDatabase::class.java,
            "country-database"
        ).build()
    }
}

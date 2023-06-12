package com.jamesthacker.countrysample.data.di

import com.jamesthacker.countrysample.data.repository.CountryRepositoryImpl
import com.jamesthacker.countrysample.domain.repository.CountryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCountryRepository(repository: CountryRepositoryImpl): CountryRepository
}

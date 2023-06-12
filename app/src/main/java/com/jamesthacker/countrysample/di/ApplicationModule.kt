package com.jamesthacker.countrysample.di

import com.jamesthacker.countrysample.data.Environment
import com.jamesthacker.countrysample.env.AppEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun bindEnvironment(appEnvironment: AppEnvironment): Environment
}

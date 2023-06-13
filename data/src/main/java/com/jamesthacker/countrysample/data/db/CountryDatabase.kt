package com.jamesthacker.countrysample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jamesthacker.countrysample.data.db.dao.CountryDao
import com.jamesthacker.countrysample.data.db.entity.CountryDetailsEntity
import com.jamesthacker.countrysample.data.db.entity.CountryEntity

@Database(entities = [CountryEntity::class, CountryDetailsEntity::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}

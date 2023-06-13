package com.jamesthacker.countrysample.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jamesthacker.countrysample.data.db.entity.CountryDetailsEntity
import com.jamesthacker.countrysample.data.db.entity.CountryEntity
import com.jamesthacker.countrysample.data.db.entity.CountryWithDetails

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    fun getAll(): List<CountryEntity>

    @Transaction
    @Query("SELECT * FROM countries WHERE name IS :name LIMIT 1")
    fun getByName(name: String): CountryWithDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg countries: CountryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg countryDetails: CountryDetailsEntity)

    @Delete
    fun delete(country: CountryEntity)

    @Delete
    fun delete(countryDetails: CountryDetailsEntity)
}

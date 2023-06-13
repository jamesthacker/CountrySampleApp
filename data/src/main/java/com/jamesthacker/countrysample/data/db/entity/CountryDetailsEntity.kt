package com.jamesthacker.countrysample.data.db.entity

import androidx.room.Entity
import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.model.LatLng

@Entity(primaryKeys = ["countryName", "commonName"], tableName = "details")
data class CountryDetailsEntity(
    val countryName: String,
    val commonName: String,
    val capital: String,
    val population: String,
    val area: String,
    val region: String,
    val subregion: String,
    val latitude: Double?,
    val longitude: Double?,
)

fun CountryDetailsEntity.toDomain(): CountryDetails {
    return CountryDetails(
        commonName,
        capital,
        population,
        area,
        region,
        subregion,
        if (latitude != null && longitude != null) {
            LatLng(latitude, longitude)
        } else {
            null
        }
    )
}

fun CountryDetails.toEntity(countryName: String): CountryDetailsEntity {
    return CountryDetailsEntity(
        countryName,
        commonName,
        capital,
        population,
        area,
        region,
        subregion,
        latLng?.latitude,
        latLng?.longitude
    )
}

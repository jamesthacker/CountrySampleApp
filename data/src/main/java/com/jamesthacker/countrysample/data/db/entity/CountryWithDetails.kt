package com.jamesthacker.countrysample.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.jamesthacker.countrysample.domain.model.Country

data class CountryWithDetails(
    @Embedded val country: CountryEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "countryName"
    )
    val details: List<CountryDetailsEntity>
)

fun CountryWithDetails.toCountryDomain(): Country {
    return Country(country.name, details.map {
        it.toDomain()
    })
}

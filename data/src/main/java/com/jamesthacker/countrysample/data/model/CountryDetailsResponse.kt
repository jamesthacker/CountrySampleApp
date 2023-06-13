package com.jamesthacker.countrysample.data.model

import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
data class CountryDetailsResponse(
    val area: Double? = null,
    val capital: List<String?> = listOf(),
    val population: Int? = null,
    val region: String? = null,
    val subregion: String? = null,
    val name: CountryNameDetails? = null,
    val latlng: List<Double> = listOf(),
) {
    @Serializable
    data class CountryNameDetails(
        val common: String
    )
}

fun CountryDetailsResponse.toDomain(): CountryDetails {
    return CountryDetails(
        commonName = name?.common.orEmpty(),
        capital = capital.firstOrNull().orEmpty(),
        population = population?.toString().orEmpty(),
        area = area?.toString().orEmpty(),
        region = region.orEmpty(),
        subregion = subregion.orEmpty(),
        latLng = latlng.toLatLng()
    )
}

private fun List<Double>.toLatLng(): LatLng? {
    return if (this.size == 2) {
        LatLng(first(), last())
    } else {
        null
    }
}

package com.jamesthacker.countrysample.data.model

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

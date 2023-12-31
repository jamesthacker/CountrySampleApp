package com.jamesthacker.countrysample.domain.model

data class CountryDetails(
    val commonName: String,
    val capital: String,
    val population: String,
    val area: String,
    val region: String,
    val subregion: String,
    val latLng: LatLng?
)

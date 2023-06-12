package com.jamesthacker.countrysample.ui.nav

sealed class NavRoutes(
    val route: String,
) {
    object CountryList: NavRoutes("countryList")
    object CountryDetails: NavRoutes("country/{$ARG_COUNTRY_NAME}")

    companion object {
        const val ARG_COUNTRY_NAME = "countryName"
    }
}

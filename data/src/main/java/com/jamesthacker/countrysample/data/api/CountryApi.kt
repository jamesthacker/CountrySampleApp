package com.jamesthacker.countrysample.data.api

import com.jamesthacker.countrysample.data.model.CountryDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    @GET("name/{countryName}")
    suspend fun getCountryDetails(
        @Path("countryName") countryName: String
    ): Response<List<CountryDetailsResponse>>
}

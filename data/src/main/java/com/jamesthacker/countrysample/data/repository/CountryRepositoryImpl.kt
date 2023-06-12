package com.jamesthacker.countrysample.data.repository

import android.content.Context
import com.jamesthacker.countrysample.data.R
import com.jamesthacker.countrysample.data.api.ApiClient
import com.jamesthacker.countrysample.data.api.CountryApi
import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.repository.CountryRepository
import com.jamesthacker.countrysample.domain.result.DomainResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiClient: ApiClient,
    private val countryApi: CountryApi
) : CountryRepository {

    override suspend fun getCountryList(): DomainResult<List<String>> {
        return DomainResult.Success(context.resources.getStringArray(R.array.countries_array).toList())
    }

    override suspend fun getCountryByName(name: String): DomainResult<List<CountryDetails>> {
        val response = apiClient.execute(
            request = {
                countryApi.getCountryDetails(name)
            },
            mapToDomain = {
                this.map {
                    CountryDetails(
                        it.name?.common.orEmpty(),
                        it.capital.firstOrNull().orEmpty(),
                        it.population?.toString().orEmpty(),
                        it.area?.toString().orEmpty(),
                        it.region.orEmpty(),
                        it.subregion.orEmpty()
                    )
                }

            }
        )
        return response
    }
}

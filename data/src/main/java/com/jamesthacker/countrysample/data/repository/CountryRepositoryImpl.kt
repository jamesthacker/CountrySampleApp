package com.jamesthacker.countrysample.data.repository

import android.content.Context
import com.jamesthacker.countrysample.data.R
import com.jamesthacker.countrysample.data.api.ApiClient
import com.jamesthacker.countrysample.data.api.CountryApi
import com.jamesthacker.countrysample.data.db.CountryDatabase
import com.jamesthacker.countrysample.data.db.entity.toCountryDomain
import com.jamesthacker.countrysample.data.db.entity.toEntity
import com.jamesthacker.countrysample.data.model.toDomain
import com.jamesthacker.countrysample.domain.model.Country
import com.jamesthacker.countrysample.domain.repository.CountryRepository
import com.jamesthacker.countrysample.domain.result.DomainResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiClient: ApiClient,
    private val countryApi: CountryApi,
    private val countryDatabase: CountryDatabase
) : CountryRepository {

    private val countryDetails = MutableStateFlow<DomainResult<Country>>(DomainResult.Uninitialized)

    override fun getCountryList(): DomainResult<List<String>> {
        return DomainResult.Success(
            data = context.resources.getStringArray(R.array.countries_array).toList(),
            isFromCache = true
        )
    }

    override suspend fun getCountryByName(name: String) {
        // Emit locally cached data first
        withContext(Dispatchers.IO) {
            val cachedData = countryDatabase.countryDao().getByName(name)
            cachedData?.let {
                if (it.country.isRecentlyUpdated()) {
                    countryDetails.value = DomainResult.Success(it.toCountryDomain())
                }
            }

            // Make network request
            val response = apiClient.execute(
                request = {
                    countryApi.getCountryDetails(name)
                },
                mapToDomain = {
                    Country(
                        name,
                        map {
                            it.toDomain()
                        }
                    )
                }
            )

            // Save latest response to local database
            if (response is DomainResult.Success) {
                countryDatabase
                    .countryDao()
                    .apply {
                        insertAll(response.data.toEntity())
                        insertAll(*response.data.details.map { it.toEntity(name) }.toTypedArray())
                    }
            }

            // StateFlow value updates are always conflated.
            // A slow collector skips fast updates,
            // but always collects the most recently emitted value.
            // This means that the cached data could not be collected
            // if we error too quickly after emitting the cached data.
            //
            // Emit network response if:
            // - Successful response
            // - Error response and cache is outdated or non-existent
            //
            // Do not emit if:
            // - Error response and cache is recent
            if (response is DomainResult.Success ||
                cachedData?.country?.isRecentlyUpdated() != true
            ) {
                countryDetails.value = response
            }
        }
    }

    override fun observeCountryDetails(): StateFlow<DomainResult<Country>> {
        return countryDetails
    }
}

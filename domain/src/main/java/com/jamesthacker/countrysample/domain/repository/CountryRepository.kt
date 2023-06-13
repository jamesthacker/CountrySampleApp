package com.jamesthacker.countrysample.domain.repository

import com.jamesthacker.countrysample.domain.model.Country
import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.result.DomainResult
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.Flow

interface CountryRepository {

    fun getCountryList(): DomainResult<List<String>>
    suspend fun getCountryByName(name: String)
    fun observeCountryDetails(): StateFlow<DomainResult<Country>>
}

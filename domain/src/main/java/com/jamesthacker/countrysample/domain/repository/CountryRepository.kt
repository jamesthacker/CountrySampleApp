package com.jamesthacker.countrysample.domain.repository

import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.result.DomainResult

interface CountryRepository {

    suspend fun getCountryList(): DomainResult<List<String>>
    suspend fun getCountryByName(name: String): DomainResult<List<CountryDetails>>
}

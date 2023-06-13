package com.jamesthacker.countrysample.data.repository

import com.jamesthacker.countrysample.data.api.ApiClient
import com.jamesthacker.countrysample.data.api.CountryApi
import com.jamesthacker.countrysample.data.mockContext
import com.jamesthacker.countrysample.data.model.CountryDetailsResponse
import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.result.DomainError
import com.jamesthacker.countrysample.domain.result.DomainResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException


class CountryRepositoryImplTest {

    private lateinit var repository: CountryRepositoryImpl
    private val countryApi = mockk<CountryApi>()

    @Before
    fun setup() {
        repository = CountryRepositoryImpl(
            mockContext,
            ApiClient(),
            countryApi
        )
    }

    @Test
    fun givenGetCountryByNameIsSuccessfulThenDomainModelIsReturned() =
        runTest {
            val mockResponse = listOf(
                CountryDetailsResponse(
                    area = 123.0,
                    capital = listOf("Capital 1", "Capital 2"),
                    population = 456,
                    region = "region",
                    subregion = "subregion",
                    name = CountryDetailsResponse.CountryNameDetails("Common name")
                )
            )
            coEvery { countryApi.getCountryDetails(any()) } returns (Response.success(mockResponse))

            val result = repository.getCountryByName("country")

            coVerify { countryApi.getCountryDetails(any()) }

            Assert.assertTrue(result is DomainResult.Success)
            val data = (result as DomainResult.Success).data
            Assert.assertEquals(1, data.size)
            val firstDetails = data.first()
            Assert.assertEquals(
                CountryDetails(
                    name = "Common name",
                    area = "123.0",
                    capital = "Capital 1",
                    population = "456",
                    region = "region",
                    subregion = "subregion",
                ),
                firstDetails
            )
        }

    @Test
    fun givenGetCountryByNameErrorsThenDomainErrorIsReturned() =
        runTest {
            coEvery { countryApi.getCountryDetails(any()) } throws UnknownHostException()

            val result = repository.getCountryByName("country")
            coVerify { countryApi.getCountryDetails(any()) }

            Assert.assertTrue(result is DomainResult.Error)
            val error = (result as DomainResult.Error).error
            Assert.assertTrue(error is DomainError.NetworkError)
        }
}

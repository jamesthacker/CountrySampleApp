package com.jamesthacker.countrysample.ui.country

import com.jamesthacker.countrysample.domain.repository.CountryRepository
import com.jamesthacker.countrysample.domain.result.DomainResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CountryListViewModelTest {

    private lateinit var viewModel: CountryListViewModel
    private val countryRepository: CountryRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        coEvery { countryRepository.getCountryList() } returns DomainResult.Success(
            listOf(
                "Country 1",
                "Country 2",
                "Country 3",
            )
        )
        viewModel = CountryListViewModel(countryRepository)
    }

    @Test
    fun givenViewModelIsInitializedThenCountriesIsRefreshed() = runTest {
        coVerify { countryRepository.getCountryList() }
        Assert.assertEquals(
            listOf(
                "Country 1",
                "Country 2",
                "Country 3",
            ),
            viewModel.uiState.value.countries
        )
    }

    @Test
    fun givenSearchTermIsUpdatedThenCountriesListIsFiltered() = runTest {
        viewModel.onSearchUpdated("2")
        Assert.assertEquals(
            listOf(
                "Country 2",
            ),
            viewModel.uiState.value.countries
        )
    }
}

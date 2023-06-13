package com.jamesthacker.countrysample.ui.country.details

import androidx.lifecycle.SavedStateHandle
import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.repository.CountryRepository
import com.jamesthacker.countrysample.domain.result.DomainError
import com.jamesthacker.countrysample.domain.result.DomainResult
import com.jamesthacker.countrysample.ui.nav.NavRoutes
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailsViewModelTest {

    private lateinit var viewModel: CountryDetailsViewModel
    private val countryRepository: CountryRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun givenViewModelIsInitializedThenCountryDetailsIsRefreshed() = runTest {
        coEvery { countryRepository.getCountryByName(any()) } returns DomainResult.Success(
            listOf(
                CountryDetails(
                    "Country Name",
                    "Capital",
                    "Population",
                    "area",
                    "region",
                    "Subregion"
                )
            )
        )
        viewModel = CountryDetailsViewModel(countryRepository, SavedStateHandle(mapOf(NavRoutes.ARG_COUNTRY_NAME to "Country 1")))
        advanceUntilIdle()

        coVerify { countryRepository.getCountryByName("Country 1") }
        Assert.assertEquals(
            listOf(
                CountryDetails(
                    "Country Name",
                    "Capital",
                    "Population",
                    "area",
                    "region",
                    "Subregion"
                )
            ),
            viewModel.uiState.value.countryDetails
        )
    }

    @Test
    fun givenDetailsEncountersErrorThenErrorIsDisplayed() = runTest {
        coEvery { countryRepository.getCountryByName(any()) } returns DomainResult.Error(
            DomainError.Unknown
        )
        viewModel = CountryDetailsViewModel(countryRepository, SavedStateHandle(mapOf(NavRoutes.ARG_COUNTRY_NAME to "Country 1")))
        advanceUntilIdle()

        coVerify { countryRepository.getCountryByName("Country 1") }
        Assert.assertEquals(
            DomainError.Unknown,
            viewModel.uiState.value.error
        )
    }
}

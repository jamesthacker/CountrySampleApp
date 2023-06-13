package com.jamesthacker.countrysample.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jamesthacker.countrysample.domain.repository.CountryRepository
import com.jamesthacker.countrysample.domain.result.DomainError
import com.jamesthacker.countrysample.domain.result.DomainResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var allCountries = listOf<String>()

    init {
        refreshCountries()
    }

    private fun refreshCountries() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true)
            }

            when(val result = countryRepository.getCountryList()) {
                is DomainResult.Success -> {
                    allCountries = result.data
                    _uiState.update {
                        it.copy(
                            loading = false,
                            countries = result.data.filterForSearchTerm(it.searchInput),
                            error = null
                        )
                    }
                }
                is DomainResult.Error -> {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = result.error
                        )
                    }
                }
            }
        }
    }

    private fun List<String>.filterForSearchTerm(searchTerm: String): List<String> {
        return filter {
            it.contains(searchTerm, ignoreCase = true)
        }
    }

    fun onSearchUpdated(searchInput: String) {
        _uiState.update {
            it.copy(
                searchInput = searchInput,
                countries = allCountries.filterForSearchTerm(searchInput)
            )
        }
    }

    data class UiState(
        val searchInput: String = "",
        val countries: List<String> = listOf(),
        val loading: Boolean = false,
        val error: DomainError? = null,
    )
}

package com.jamesthacker.countrysample.ui.country.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.repository.CountryRepository
import com.jamesthacker.countrysample.domain.result.DomainError
import com.jamesthacker.countrysample.domain.result.DomainResult
import com.jamesthacker.countrysample.ui.nav.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val repository: CountryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    private val countryName = savedStateHandle.get<String>(NavRoutes.ARG_COUNTRY_NAME).orEmpty()

    init {
        _uiState.update {
            it.copy(
                appBarTitle = countryName,
            )
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    loading = true,
                )
            }
            when (val result = repository.getCountryByName(countryName)) {
                is DomainResult.Success -> {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            countryDetails = result.data
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

    data class UiState(
        val appBarTitle: String = "",
        val countryDetails: List<CountryDetails> = listOf(),
        val loading: Boolean = false,
        val error: DomainError? = null,
    )
}

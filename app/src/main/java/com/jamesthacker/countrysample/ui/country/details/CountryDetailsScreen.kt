package com.jamesthacker.countrysample.ui.country.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.jamesthacker.countrysample.R
import com.jamesthacker.countrysample.domain.model.CountryDetails
import com.jamesthacker.countrysample.domain.result.DomainError
import com.jamesthacker.countrysample.ext.on
import com.jamesthacker.countrysample.ui.common.AppTopBar
import com.jamesthacker.countrysample.ui.common.ErrorDialog
import com.jamesthacker.countrysample.ui.theme.Dimens

@Composable
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    CountryDetailsContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun CountryDetailsContent(
    uiState: CountryDetailsViewModel.UiState,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppTopBar(
            title = uiState.appBarTitle,
            navigationIcon = rememberVectorPainter(image = Icons.Default.ArrowBack),
            navButtonContentDescription = stringResource(id = R.string.back_content_description),
            onActionClick = onNavigateBack
        )

        if (uiState.loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.medium),
            verticalArrangement = Arrangement.spacedBy(Dimens.medium)
        ) {
            items(uiState.countryDetails.size) { index ->
                val details = uiState.countryDetails[index]
                CountryDetailsSection(
                    details,
                    modifier = Modifier.on(!uiState.loading) {
                        padding(top = Dimens.xSmall)
                    }
                )
            }
        }

        ErrorDialog(uiState.error) {
            onNavigateBack.invoke()
        }
    }
}

@Composable
private fun CountryDetailsSection(
    countryDetails: CountryDetails?,
    modifier: Modifier = Modifier,
) {
    if (countryDetails != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(bottom = Dimens.small),
                text = countryDetails.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            CountryDetail(
                key = stringResource(R.string.capital),
                value = countryDetails.capital
            )
            CountryDetail(
                key = stringResource(R.string.population),
                value = countryDetails.population
            )
            CountryDetail(
                key = stringResource(R.string.area),
                value = countryDetails.area
            )
            CountryDetail(
                key = stringResource(R.string.region),
                value = countryDetails.region
            )
            CountryDetail(
                key = stringResource(R.string.subregion),
                value = countryDetails.subregion
            )
        }
    }
}

@Composable
private fun CountryDetail(
    key: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(key, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Text(value, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Preview
@Composable
fun PreviewCountryDetailsScreen() {
    CountryDetailsContent(
        uiState = CountryDetailsViewModel.UiState(
            "Argentina",
            countryDetails = listOf(
                CountryDetails(
                    name = "Argentina",
                    capital = "Buenos Aires",
                    population = "45376763",
                    area = "2780400.0",
                    region = "Americas",
                    subregion = "South America"
                )
            ),
        ),
        onNavigateBack = {}
    )
}

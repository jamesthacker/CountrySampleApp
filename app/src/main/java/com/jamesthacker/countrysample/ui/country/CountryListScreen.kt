package com.jamesthacker.countrysample.ui.country

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jamesthacker.countrysample.R
import com.jamesthacker.countrysample.ui.common.AppTopBar
import com.jamesthacker.countrysample.ui.theme.Dimens

@Composable
fun CountryListScreen(
    viewModel: CountryListViewModel,
    onNavigateToCountryDetails: (String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value
    CountryListContent(
        uiState = uiState,
        onItemClick = onNavigateToCountryDetails,
        onSearchUpdated = {
            viewModel.onSearchUpdated(it)
        }
    )
}

@Composable
private fun CountryListContent(
    uiState: CountryListViewModel.UiState,
    onItemClick: (String) -> Unit,
    onSearchUpdated: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.medium)
    ) {
        AppTopBar(
            title = "Countries",
            navigationIcon = null,
            navButtonContentDescription = null
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.searchInput,
            placeholder = {
                Text(stringResource(R.string.search))
            },
            onValueChange = {
                onSearchUpdated(it)
            },
            leadingIcon = {
                Icon(Icons.Default.Search, stringResource(R.string.search_content_description))
            },
            trailingIcon = {
                IconButton(onClick = {
                    onSearchUpdated("")
                }) {
                    Icon(
                        Icons.Default.Clear,
                        stringResource(R.string.clear_search_content_description)
                    )
                }
            }
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(uiState.countries.size) { index ->
                val countryName = uiState.countries[index]
                CountryItem(name = countryName) {
                    onItemClick.invoke(it)
                }
            }
        }
    }
}

@Composable
private fun CountryItem(
    name: String,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = {
            onClick.invoke(name)
        }
    ) {
        Text(name)
    }
}

@Preview
@Composable
private fun PreviewCountryListContent() {
    CountryListContent(
        uiState = CountryListViewModel.UiState(
            countries = listOf("Country 1", "Country 2", "Country 3")
        ),
        onItemClick = {},
        onSearchUpdated = {}
    )
}

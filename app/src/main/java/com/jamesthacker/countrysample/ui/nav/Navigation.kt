package com.jamesthacker.countrysample.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jamesthacker.countrysample.ui.country.CountryListScreen
import com.jamesthacker.countrysample.ui.country.details.CountryDetailsScreen

fun String.withArgument(key: String, value: String): String =
    this.replace("{$key}", value)

@Composable
fun MainNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NavRoutes.CountryList.route
    ) {
        composable(NavRoutes.CountryList.route) {
            CountryListScreen(
                viewModel = hiltViewModel(),
                onNavigateToCountryDetails = {
                    val path = NavRoutes.CountryDetails.route.withArgument(
                        key = NavRoutes.ARG_COUNTRY_NAME,
                        value = it
                    )
                    navHostController.navigate(path)
                }
            )
        }

        composable(
            route = NavRoutes.CountryDetails.route,
            arguments = listOf(
                navArgument(
                    NavRoutes.ARG_COUNTRY_NAME
                ) {
                    type = NavType.StringType
                    nullable = false
                }
            )) {
            CountryDetailsScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = {
                    navHostController.popBackStack()
                },
            )
        }
    }
}


package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.location.LocationPermissionScreen
import com.example.weatherapp.presentation.SettingsScreen
import com.example.weatherapp.presentation.search.SearchScreen
import com.example.weatherapp.ui.HomeScreen
import com.example.weatherapp.util.WeatherViewModel

object WeatherRoutes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val SETTINGS = "settings"
}

@Composable
fun WeatherNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel
) {
        NavHost(
            navController = navController,
            startDestination = WeatherRoutes.HOME,
            modifier = modifier
        ) {
            composable("home") { HomeScreen(viewModel = weatherViewModel) }
            composable("search") { SearchScreen() }
            composable("settings") { SettingsScreen() }
        }
}

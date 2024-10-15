package com.example.weatherapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weatherapp.location.LocationPermissionScreen
import com.example.weatherapp.location.PermissionDeniedDialog
import com.example.weatherapp.navigation.NavItem
import com.example.weatherapp.navigation.WeatherBottomNavigationBar
import com.example.weatherapp.navigation.WeatherNavHost
import com.example.weatherapp.util.WeatherViewModel

@Composable
fun MainScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Search", Icons.Default.Search),
        NavItem("Settings", Icons.Default.Settings)
    )

    val weatherViewModel: WeatherViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var isDialogShown: Boolean by remember { mutableStateOf(false) }
    if (isDialogShown) {
        PermissionDeniedDialog { isDialogShown = false }
    }
    if (!weatherViewModel.hasPermission) {
        LocationPermissionScreen(permissionGranted = {
            isDialogShown = false
            weatherViewModel.refreshData()
            weatherViewModel.hasPermission = true
        }, permissionDenied = {
            isDialogShown = true
        })
    } else
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                WeatherBottomNavigationBar(
                    navController = navController,
                    navItemList = navItemList,
                    currentDestination = currentDestination
                )
            }
        ) { innerPadding ->
            WeatherNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                weatherViewModel = weatherViewModel
            )
        }
}

@Composable
fun SettingsScreen() {
    Text(text = "Settings Screen", modifier = Modifier.fillMaxSize())
}



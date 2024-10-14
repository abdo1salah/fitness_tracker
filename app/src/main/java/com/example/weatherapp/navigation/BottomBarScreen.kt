package com.example.weatherapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.navigation.NavItem
import com.example.weatherapp.navigation.WeatherNavHost
import com.example.weatherapp.util.WeatherViewModel


@Composable
fun WeatherBottomNavigationBar(
    navController: NavHostController,
    navItemList: List<NavItem>,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)  // Set a fixed height for the bottom bar (56dp is a typical height for bottom nav bars)
            .clip(RoundedCornerShape(20.dp)),  // Keep moderate corner rounding
        containerColor = MaterialTheme.colors.surface
    ) {
        navItemList.forEach { navItem ->
            NavigationBarItem(
                selected = currentDestination?.route == navItem.name.lowercase(),
                onClick = {
                    navController.navigate(navItem.name.lowercase()) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.name,
                        tint = if (currentDestination?.route == navItem.name.lowercase()) {
                            MaterialTheme.colors.primary
                        } else {
                            MaterialTheme.colors.onSurface
                        }
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

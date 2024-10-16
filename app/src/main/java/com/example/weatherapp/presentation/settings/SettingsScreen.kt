package com.example.weatherapp.presentation.settings


import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.util.WeatherViewModel

@Composable
fun SettingsScreen(viewModel: WeatherViewModel) {

    val sharedPref = LocalContext.current.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    viewModel.selectedTempUnit = sharedPref.getString("Temperature Unit","Celsius (°C)")!!
    viewModel.selectedWindSpeedUnit = sharedPref.getString("Wind Speed Unit","Kilometers (km/h)")!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(56.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Settings",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary)

        // Temperature Unit Dropdown
        DropdownSettingItem(
            title = "Temperature Unit",
            options = listOf("Celsius (°C)", "Fahrenheit (°F)"),
            selectedOption = viewModel.selectedTempUnit,
            onOptionSelected = {
                Log.d("trace", it)
                viewModel.selectedTempUnit = it }
        )

        // Wind Speed Unit Dropdown
        DropdownSettingItem(
            title = "Wind Speed Unit",
            options = listOf("Kilometers (km/h)", "Miles (mph)"),

            selectedOption = viewModel.selectedWindSpeedUnit,
            onOptionSelected = { viewModel.selectedWindSpeedUnit = it }
        )

    }
}

@Composable
fun DropdownSettingItem(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    with(sharedPref.edit()) {
        putString(title, selectedOption)
        apply()
    }

    Column {
        Text(
            text = title,
            fontSize = 24.sp,
            color = MaterialTheme.colors.primary // Set title color to primary
        )

        Box {
            Text(
                text = selectedOption,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(8.dp)
                    .background(MaterialTheme.colors.surface),
                color = MaterialTheme.colors.primary // Set selected option text color to primary
            )

            DropdownMenu(
                modifier = Modifier.background(Color.LightGray),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                color = Color.Black // Dropdown menu item color
                            )
                        },
                        onClick = {
                            with(sharedPref.edit()) {
                                putString(title, option)
                                apply()
                            }
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

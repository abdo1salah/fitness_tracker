package com.example.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen() {
    var viewModel: WeatherViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(56.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Settings", fontSize = 36.sp, fontWeight = FontWeight.Bold)

        // Temperature Unit Dropdown
        DropdownSettingItem(
            title = "Temperature Unit",
            options = listOf("Celsius (°C)", "Fahrenheit (°F)"),
            selectedOption = viewModel.selectedTempUnit,
            onOptionSelected = { viewModel.selectedTempUnit = it }
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
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = title,
            fontSize = 24.sp)
        Box {
            Text(
                text = selectedOption,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(8.dp)


            )
            DropdownMenu(
                modifier = Modifier
                    .background(Color.LightGray),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options
                    .forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option,
                                color = Color.Black) },
                            onClick = {
                                onOptionSelected(option)
                                expanded = false
                            }
                        )
                    }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsScreen()
}

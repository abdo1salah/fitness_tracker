package com.example.weatherapp.util

import com.example.weatherapp.data.model.WeatherResponse

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weatherResponse: WeatherResponse) : WeatherUiState()
    object Error : WeatherUiState()
}

package com.example.weatherapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {
    var weatherResponse by mutableStateOf<WeatherResponse?>(null)
    var selectedTempUnit by mutableStateOf("Celsius (°C)")
    var selectedWindSpeedUnit by mutableStateOf("Kilometers (km/h)")

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(WeatherApiService::class.java)

    fun fetchWeather(query: String) {
        viewModelScope.launch {
            try {
                weatherResponse = api.getCurrentWeather("YOUR_API_KEY", query)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun convertTemperature(tempCelsius: Double): Double {
        return if (selectedTempUnit == "Fahrenheit (°F)") tempCelsius * 9 / 5 + 32 else tempCelsius
    }
}


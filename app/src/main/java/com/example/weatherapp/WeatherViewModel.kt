package com.example.weatherapp
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Api.WeatherApi
import com.example.weatherapp.Api.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel:ViewModel(){
    var weatherData : WeatherResponse? by mutableStateOf(null)
    val url =
        "/v1/forecast.json?key=9b2015cab9ff4b2099f214519240110&q=30.0444,31.2357&days=2&alerts=yes"
   private fun getWeatherData(){
        viewModelScope.launch {
            val result = WeatherApi.retrofitService.getData(url)
            weatherData = result
        }


        }
    init {
        getWeatherData()
    }
    }

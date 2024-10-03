package com.example.weatherapp
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Api.ENDPOINT
import com.example.weatherapp.Api.WeatherApi
import com.example.weatherapp.Api.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel():ViewModel(){
    var weatherData : WeatherResponse? by mutableStateOf(null)
   private fun getWeatherData(){
        viewModelScope.launch {
            val result = WeatherApi.retrofitService.getData(ENDPOINT)
            weatherData = result
        }

        }

    init {
        getWeatherData()
    }


    }
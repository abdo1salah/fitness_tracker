package com.example.weatherapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Api.Alerts
import com.example.weatherapp.Api.Current
import com.example.weatherapp.Api.ENDPOINT
import com.example.weatherapp.Api.WeatherApi
import com.example.weatherapp.Api.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = WeatherRepo(app)
    var casheddata: WeatherResponse? by mutableStateOf(null)
    private fun refreshData(){
        viewModelScope.launch {
            try {
                repo.refreshData()
            }
            catch (e :Exception){
                Log.d("trace",e.message.toString())
            }

            casheddata = repo.getCashedData()
        }
    }
    init {
        refreshData()
    }

    /* var weatherData : WeatherResponse? by mutableStateOf(null)
    private fun getWeatherData(){
         viewModelScope.launch {
             val result = WeatherApi.retrofitService.getData(ENDPOINT)
             weatherData = result
         }

         }

     init {
         getWeatherData()
     }*/


}
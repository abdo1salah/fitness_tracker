package com.example.weatherapp.util

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.SearchApi.SEARCHENDPOINT
import com.example.weatherapp.data.SearchApi.SearchApi
import com.example.weatherapp.data.SearchApi.SearchItem
import com.example.weatherapp.data.SearchApi.getSearchEndPoint
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.repository.WeatherRepo
import com.example.weatherapp.location.CheckRequirements
import kotlinx.coroutines.launch

class WeatherViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = WeatherRepo(app)
    var casheddata: WeatherResponse? by mutableStateOf(null)
    var searchData: List<SearchItem> by mutableStateOf(emptyList())
    var hasPermission: Boolean by mutableStateOf(true)
    var hasInternet: Boolean by mutableStateOf(true)
    var hasGps: Boolean by mutableStateOf(true)
    var selectedWindSpeedUnit by mutableStateOf("")
    var selectedTempUnit by mutableStateOf("")
    fun updateGpsState(context: Context) {
        hasGps = CheckRequirements.checkGpsState(context)
    }
    fun refreshData() {
        viewModelScope.launch {
            try {
                repo.refreshData()
            } catch (e: Exception) {
                Log.d("trace", e.message.toString())
                when {
                    e.message!!.contains("Internet") -> hasInternet = false
                    else -> hasGps = false
                }
            }

            casheddata = repo.getCashedData()
        }
    }

    fun updateSearchData(search: String) {
        getSearchEndPoint(search)
        viewModelScope.launch {
            try {
                searchData = SearchApi.searchService.getSearchData(SEARCHENDPOINT)
            } catch (e: Exception) {
                Log.d("trace", e.message.toString())
            }

        }

    }

    // Fetch weather data for a specific latitude and longitude
    fun fetchWeatherDataForLocation(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                casheddata = repo.getWeatherDataForLocation(lat, lon)
            } catch (e: Exception) {
                Log.d("trace", e.message.toString())
            }
        }
    }

    init {
        viewModelScope.launch {
            casheddata = repo.getCashedData()
        }
        hasPermission = CheckRequirements.hasPermission(app)
        refreshData()
    }


}

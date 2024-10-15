package com.example.weatherapp.util

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherRepo
import com.example.weatherapp.data.SearchApi.SEARCHENDPOINT
import com.example.weatherapp.data.SearchApi.SearchApi
import com.example.weatherapp.data.SearchApi.SearchItem
import com.example.weatherapp.data.SearchApi.getSearchEndPoint
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.location.CheckRequirements
import kotlinx.coroutines.launch

class WeatherViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = WeatherRepo(app)
    var casheddata: WeatherResponse? by mutableStateOf(null)
    var hasInternetConnection by mutableStateOf(true)
    var hasGps: Boolean by mutableStateOf(true)
    var hasPermission: Boolean by mutableStateOf(true)
    var firstTime:Boolean by mutableStateOf(false)
    var selectedWindSpeedUnit by mutableStateOf("")
    var selectedTempUnit by mutableStateOf("")
    var searchData : List<SearchItem> by mutableStateOf(emptyList())
    fun refreshData() {
        viewModelScope.launch {
            try {
                repo.refreshData()
            } catch (e: Exception) {
                when {
                    e.message?.contains("Gps")!! -> {
                        hasGps = false
                    }

                    else -> {
                        hasInternetConnection = false
                    }
                }
            }
            casheddata = repo.getCashedData()
            if(casheddata == null) firstTime = true
        }
    }

    init {
        viewModelScope.launch {
            casheddata = repo.getCashedData()
        }
        hasPermission = CheckRequirements.hasPermission(app)
        refreshData()
    }
    fun updateSearchData(search:String){
        getSearchEndPoint(search)
        viewModelScope.launch {
            try {
                searchData = SearchApi.searchService.getSearchData(SEARCHENDPOINT)
            }
            catch (e :Exception){
                Log.d("trace",e.message.toString())
            }

        }

    }

}
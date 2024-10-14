package com.example.weatherapp.util

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.SearchApi.SEARCHENDPOINT
import com.example.weatherapp.data.SearchApi.SearchApi
import com.example.weatherapp.data.SearchApi.SearchItem
import com.example.weatherapp.data.SearchApi.getSearchEndPoint
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.data.repository.WeatherRepo
import kotlinx.coroutines.launch

class WeatherViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = WeatherRepo(app)
    var casheddata: WeatherResponse? by mutableStateOf(null)
    var searchData : List<SearchItem> by mutableStateOf(emptyList())

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


    init {
        refreshData()
    }





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

//fun getTodayForecast(): List<Hour> {
//
//            return casheddata?.forecast?.forecastday?.firstOrNull()?.hour ?: emptyList()
//        }
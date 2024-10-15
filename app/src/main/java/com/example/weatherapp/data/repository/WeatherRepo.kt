package com.example.weatherapp.data.repository

import android.content.Context
import android.util.Log
import com.example.weatherapp.data.network.ENDPOINT
import com.example.weatherapp.data.local.DBHelper
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.network.changeWeatherLocation


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class WeatherRepo(context: Context) {
    private val db = DBHelper.getDBInstance(context)
    //val cashedData = db.weetherDao().getWeather()
    //private val locationData = LocationData(context)
    suspend fun getCashedData(): WeatherResponse {
        return db.weetherDao().getWeather()
    }

   /* suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val weatherData = WeatherApi.retrofitService.getData(ENDPOINT)
            db.weetherDao().insertWeatherData(weatherData)
        }
    }*/
   suspend fun refreshData() {
       withContext(Dispatchers.IO) {
           Log.d("trace","inside refresh data")
           //val locationData = locationData.getLastLocation().first()
           //Log.d("trace",locationData!!.longitude.toString())
           val weatherData = WeatherApi.retrofitService.getData(ENDPOINT)
           db.weetherDao().insertWeatherData(weatherData)
           Log.d("trace","data is inserted")
       }
   }

    // Refresh weather data for a specific location
    suspend fun getWeatherDataForLocation(lat: String, lon: String): WeatherResponse {
        return withContext(Dispatchers.IO) {
            changeWeatherLocation(lat, lon)
            val locationWeatherData = WeatherApi.retrofitService.getData(ENDPOINT)
            db.weetherDao().insertWeatherData(locationWeatherData)
            locationWeatherData
        }
    }
}
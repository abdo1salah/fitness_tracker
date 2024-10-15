package com.example.weatherapp

import android.content.Context
import com.example.weatherapp.Api.ENDPOINT
import com.example.weatherapp.data.local.DBHelper
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class WeatherRepo(context: Context) {
    private val db = DBHelper.getDBInstance(context)
    private val locationData = LocationData(context)
    suspend fun getCashedData() :WeatherResponse = db.weetherDao().getWeather()


    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
                val locationData = locationData.getLastLocation().first()
                val weatherData = WeatherApi.retrofitService.getData(getEndPoint(locationData!!.latitude,locationData.longitude))
                db.weetherDao().insertWeatherData(weatherData)
        }
    }
}


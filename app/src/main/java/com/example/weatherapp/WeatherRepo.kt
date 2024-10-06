package com.example.weatherapp

import android.content.Context
import com.example.weatherapp.Api.ENDPOINT
import com.example.weatherapp.Api.WeatherApi
import com.example.weatherapp.Api.WeatherResponse
import com.example.weatherapp.Api.getEndPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class WeatherRepo(context: Context) {
    private val db = DBHelper.getDBInstance(context)
    private val location = Location(context)
    //val cashedData = db.weetherDao().getWeather()
    suspend fun getCashedData(): WeatherResponse {
        return db.weetherDao().getWeather()
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
           val locationData = location.getLastLocation().first()
            val weatherData = WeatherApi.retrofitService.getData(getEndPoint(locationData!!.latitude,locationData.longitude))
            db.weetherDao().insertWeatherData(weatherData)
        }
    }
}
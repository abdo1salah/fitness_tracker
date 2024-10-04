package com.example.weatherapp

import android.content.Context
import com.example.weatherapp.Api.ENDPOINT
import com.example.weatherapp.Api.WeatherApi
import com.example.weatherapp.Api.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepo(context: Context) {
    private val db = DBHelper.getDBInstance(context)
    //val cashedData = db.weetherDao().getWeather()
    suspend fun getCashedData(): WeatherResponse {
        return db.weetherDao().getWeather()
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val weatherData = WeatherApi.retrofitService.getData(ENDPOINT)
            db.weetherDao().insertWeatherData(weatherData)
        }
    }
}
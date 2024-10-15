package com.example.weatherapp

import android.content.Context
import android.util.Log

import com.example.weatherapp.data.local.DBHelper
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.network.getEndPoint
import com.example.weatherapp.location.LocationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class WeatherRepo(context: Context) {
    private val db = DBHelper.getDBInstance(context)
    private val locationData = LocationData(context)
    suspend fun getCashedData() : WeatherResponse = db.weetherDao().getWeather()


    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
                val locationData = locationData.getLastLocation().first()
                Log.d("trace","fetch location")
                val weatherData = WeatherApi.retrofitService.getData(getEndPoint(locationData!!.latitude,locationData.longitude))
                db.weetherDao().insertWeatherData(weatherData)
                Log.d("trace","data is inserted")
        }
    }
}
package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.weatherapp.data.model.WeatherResponse

@Dao
interface WeatherDao {
    @Upsert
    suspend fun insertWeatherData(weatherResponse: WeatherResponse)
    @Query("select * from weather")
    suspend fun getWeather(): WeatherResponse
}
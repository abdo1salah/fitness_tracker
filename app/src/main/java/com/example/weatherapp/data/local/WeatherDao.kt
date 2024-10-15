package com.example.weatherapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.Api.WeatherResponse

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherResponse: WeatherResponse)
    @Query("select * from weather")
    suspend fun getWeather(): WeatherResponse
}
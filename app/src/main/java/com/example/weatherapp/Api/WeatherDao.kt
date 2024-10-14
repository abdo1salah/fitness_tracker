package com.example.weatherapp.Api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherResponse: WeatherResponse)
    @Query("select * from weather")
    suspend fun getWeather(): WeatherResponse
}
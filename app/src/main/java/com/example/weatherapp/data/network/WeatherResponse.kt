package com.example.weatherapp.data.network

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapp.data.model.Alerts
import com.example.weatherapp.data.model.Converters
import com.example.weatherapp.data.model.Current
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Location

@Entity(tableName = "weather")
@TypeConverters(Converters::class)
data class WeatherResponse(
    @PrimaryKey
    val id:Int = 0,
    @Embedded
    val alerts: Alerts,
    @Embedded("current")
    val current: Current,
    @Embedded("forecast")
    val forecast: Forecast,
    @Embedded("location")
    val location: Location
)
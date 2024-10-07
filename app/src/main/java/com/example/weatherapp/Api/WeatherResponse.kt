package com.example.weatherapp.Api

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

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
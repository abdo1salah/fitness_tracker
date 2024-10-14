package com.example.weatherapp.data.model

import androidx.room.Embedded

data class Current(
    val cloud: Int,
    @Embedded
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val humidity: Int,
    val is_day: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Double,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val vis_km: Double,
    val wind_degree: Int,
    val wind_kph: Double,
    val wind_mph: Double
)
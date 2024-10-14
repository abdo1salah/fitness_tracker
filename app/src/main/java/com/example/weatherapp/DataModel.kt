package com.example.weatherapp

data class WeatherResponse(val location: Location, val current: Current)
data class Location(val name: String, val region: String)
data class Current(val temp_c: Double, val temp_f: Double)
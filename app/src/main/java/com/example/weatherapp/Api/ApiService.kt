package com.example.weatherapp.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://api.weatherapi.com"

val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
var ENDPOINT = "/v1/forecast.json?key=85a444f821fd430886f192139240510&q=30.0444,31.2357&days=3&alerts=yes"
fun changeWeatherLocation(lat: String, long: String){
    ENDPOINT = "/v1/forecast.json?key=85a444f821fd430886f192139240510&q=${lat},${long}&days=3&alerts=yes"
}
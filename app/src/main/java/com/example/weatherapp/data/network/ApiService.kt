package com.example.weatherapp.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://api.weatherapi.com"

val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
var ENDPOINT =
    "/v1/forecast.json?key=9b2015cab9ff4b2099f214519240110&q=30.0444,31.2357&days=2&alerts=yes"

fun getEndPoint(lat: Double, long: Double): String =
    "/v1/forecast.json?key=9b2015cab9ff4b2099f214519240110&q=${lat},${long}&days=2&alerts=yes"

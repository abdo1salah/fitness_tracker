package com.example.weatherapp.data.network

object WeatherApi {
    val retrofitService : WeatherCallable by lazy {
        retrofit.create(WeatherCallable::class.java)

    }
}
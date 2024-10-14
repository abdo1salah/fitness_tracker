package com.example.weatherapp.Api

object WeatherApi {
    val retrofitService : WeatherCallable by lazy {
        retrofit.create(WeatherCallable::class.java)

    }
}
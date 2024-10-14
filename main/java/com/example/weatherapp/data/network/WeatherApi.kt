package com.example.weatherapp.data.network

import com.example.weatherapp.Api.retrofit

object WeatherApi {
    val retrofitService : WeatherCallable by lazy {
        retrofit.create(WeatherCallable::class.java)

    }
}
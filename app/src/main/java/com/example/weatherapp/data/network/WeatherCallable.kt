package com.example.weatherapp.data.network

import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherCallable {
    @GET
    suspend fun getData(@Url url:String): WeatherResponse
}
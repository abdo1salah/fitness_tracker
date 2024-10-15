package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherCallable {
    @GET
    suspend fun getData(@Url url:String): WeatherResponse

}
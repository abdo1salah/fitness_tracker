package com.example.weatherapp.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherCallable {
    @GET
    fun getData(@Url url:String): Call<WeatherResponse>
}
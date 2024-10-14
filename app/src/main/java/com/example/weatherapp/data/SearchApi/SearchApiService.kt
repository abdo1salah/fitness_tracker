package com.example.weatherapp.data.SearchApi

import com.example.weatherapp.Api.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val searchRetrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

var SEARCHENDPOINT = "/v1/search.json?key=85a444f821fd430886f192139240510&q="
fun getSearchEndPoint(search:String){
    SEARCHENDPOINT = "/v1/search.json?key=85a444f821fd430886f192139240510&q=${search}"
}
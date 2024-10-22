package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.SearchData
import retrofit2.http.GET
import retrofit2.http.Url

interface SearchCallable {
    @GET
    suspend fun getSearchData(@Url url:String) : SearchData
}
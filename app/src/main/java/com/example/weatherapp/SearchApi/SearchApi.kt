package com.example.weatherapp.SearchApi

import com.example.weatherapp.Api.retrofit

object SearchApi {
    val searchService : SearchCallable by lazy {
        searchRetrofit.create(SearchCallable::class.java)
    }
}
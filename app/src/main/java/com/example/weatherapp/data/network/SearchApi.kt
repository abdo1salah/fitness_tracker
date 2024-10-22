package com.example.weatherapp.data.network

object SearchApi {
    val searchService : SearchCallable by lazy {
        searchRetrofit.create(SearchCallable::class.java)
    }
}
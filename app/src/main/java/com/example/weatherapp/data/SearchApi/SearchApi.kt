package com.example.weatherapp.data.SearchApi

object SearchApi {
    val searchService : SearchCallable by lazy {
        searchRetrofit.create(SearchCallable::class.java)
    }
}
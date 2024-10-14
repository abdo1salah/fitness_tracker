package com.example.weatherapp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Api.WeatherResponse
import com.example.weatherapp.location.CheckRequirements
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = WeatherRepo(app)
    var casheddata: WeatherResponse? by mutableStateOf(null)
    var hasInternetConnection by mutableStateOf(true)
    var hasGps: Boolean by mutableStateOf(true)
    var hasPermission: Boolean by mutableStateOf(true)
    var isRefershing = MutableStateFlow(false)
    fun refreshData() {
        viewModelScope.launch {
            try {
                repo.refreshData()
            } catch (e: Exception) {
                when {
                    e.message?.contains("Gps")!! -> {
                        hasGps = false
                    }

                    else -> {
                        hasInternetConnection = false
                    }
                }
            }
            casheddata = repo.getCashedData()
        }
    }

    init {
        viewModelScope.launch {
            casheddata = repo.getCashedData()
        }
        hasPermission = CheckRequirements.hasPermission(app)
        refreshData()
    }

    fun refresh() {
    isRefershing.update { true }
        viewModelScope.launch {
            refreshData()
            isRefershing.update { false }
        }
    }
}
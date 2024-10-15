package com.example.weatherapp.data.model

import androidx.room.Embedded

data class Hour(
    val time: String,
    val temp_c: Double,
    @Embedded
    val condition: Condition
)

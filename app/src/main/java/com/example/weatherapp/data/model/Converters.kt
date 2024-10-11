package com.example.weatherapp.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
        @TypeConverter
        fun listToJson(value: List<Alert>?) = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<Alert>::class.java).toList()
    @TypeConverter
    fun foreCastDayJistToJson(value: List<Forecastday>?) = Gson().toJson(value)

    @TypeConverter
    fun foreCastDayJsonToList(value: String) = Gson().fromJson(value, Array<Forecastday>::class.java).toList()
    }

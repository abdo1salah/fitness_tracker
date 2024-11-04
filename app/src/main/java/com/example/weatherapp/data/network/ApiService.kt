package com.example.weatherapp.data.network

import android.content.Context
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://api.weatherapi.com"

var ENDPOINT = "/v1/forecast.json?key=85a444f821fd430886f192139240510&q=30.0444,31.2357&days=3&alerts=yes"

fun changeWeatherLocation(lat: String, long: String){
    ENDPOINT = "/v1/forecast.json?key=85a444f821fd430886f192139240510&q=${lat},${long}&days=3&alerts=yes"
}
fun getEndPoint(lat: Double,long: Double):String = "/v1/forecast.json?key=85a444f821fd430886f192139240510&q=${lat},${long}&days=3&alerts=yes"
fun checkLocationSetting(
    context: Context,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: () -> Unit
) {

    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000)
        //.setMinUpdateIntervalMillis(0L)
        //.setMaxUpdates(1)
        .build()

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val builder: LocationSettingsRequest.Builder = LocationSettingsRequest
        .Builder()
        .addLocationRequest(locationRequest)

    val gpsSettingTask: Task<LocationSettingsResponse> =
        client.checkLocationSettings(builder.build())

    gpsSettingTask.addOnSuccessListener { onEnabled() }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest
                    .Builder(exception.resolution)
                    .build()
                onDisabled(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                // ignore here
            }
        }
    }

}
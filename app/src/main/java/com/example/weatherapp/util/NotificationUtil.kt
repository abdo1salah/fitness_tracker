package com.example.weatherapp.util

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R

class NotificationUtil {
    object NotificationUtil {
        @SuppressLint("MissingPermission")
        fun sendWeatherNotification(context: Context, weatherData: WeatherViewModel) {
            val condition = weatherData.casheddata?.current?.condition?.text
            val location = weatherData.casheddata?.location?.name
            val region = weatherData.casheddata?.location?.region
            val maxtempC = weatherData.casheddata?.forecast?.forecastday?.get(0)?.day?.maxtemp_c
            val mintempC = weatherData.casheddata?.forecast?.forecastday?.get(0)?.day?.mintemp_c

            Log.d("WeatherData", "TempC: $mintempC, ")

            val weatherDescription = "${region ?: "N/A"}, ${location ?: "N/A"} highs to ${maxtempC ?: "N/A"}C and lows to ${mintempC ?: "N/A"}C, ${condition ?: "N/A"}"

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.preview_cloudy)
                .setContentTitle("Today's Weather")
                .setContentText(weatherDescription)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(99, notification)
        }
    }
}
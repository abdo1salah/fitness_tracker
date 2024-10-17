package com.example.weatherapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weatherapp.data.repository.WeatherRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationReceiver : BroadcastReceiver() {
    companion object {
        private const val REQUEST_CODE_MAIN_ACTIVITY = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationReceiver", "onReceive: alarm Triggered")
        val weatherRepo = WeatherRepo(context)
        CoroutineScope(Dispatchers.IO).launch {
            val weatherData = weatherRepo.getCashedData()
            withContext(Dispatchers.Main) {
                // Use the weather data to create the notification
                val condition = weatherData.current?.condition?.text
                val location = weatherData.location?.name
                val region = weatherData.location?.region
                val maxtempC = weatherData.forecast?.forecastday?.get(0)?.day?.maxtemp_c
                val mintempC = weatherData.forecast?.forecastday?.get(0)?.day?.mintemp_c
                val intent = Intent(context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(
                    context.applicationContext,
                    REQUEST_CODE_MAIN_ACTIVITY,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
                val weatherDescription =
                    " ${region ?: "N/A"},  ${location ?: "N/A"} highs to ${maxtempC ?: "N/A"}C and lows to ${mintempC ?: "N/A"}C ,  ${condition ?: "N/A"}"

                val builder = NotificationCompat.Builder(context, "weather_alerts")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("Today's Weather")
                    .setContentText(weatherDescription)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

                // Create the notification channel (required for Android 8.0 and above)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        "weather_alerts",
                        "Weather Alerts",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    val notificationManager =
                        context.getSystemService(NotificationManager::class.java)
                    notificationManager?.createNotificationChannel(channel)
                }

                // Show the notification
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(1, builder.build())
            }
        }
    }
}
package com.example.weatherapp.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.weatherapp.R
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class LocationData(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Flow<Location?> = callbackFlow {
        if (
            CheckRequirements.hasPermission(context)
        ) {
            if (!CheckRequirements.checkInternetState(context)) {
                throw Exception("No Internet Connection")
            } else if (!CheckRequirements.checkGpsState(context)) {
                Log.d("trace", "LocationData: Gps")
                throw Exception("Gps is OFF")
            } else
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        // Do something with the location
                        Log.d(
                            "trace",
                            "LocationData: ${location.latitude}, ${location.longitude}"
                        )
                        launch {
                            send(location)
                        }
                    }
                }
        }
        awaitClose {
        }
    }

}

@Composable
fun RequestGpsAlertDialog(confirmButton: () -> Unit, dismissButton: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = { TextButton(onClick = { confirmButton() }) { Text("OK") } },
        dismissButton = { TextButton(onClick = { dismissButton() }) { Text("No, thanks") } },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_warning_24),
                contentDescription = "warning"
            )
        },
        text = { Text("For a better experience turn on device location") },
    )
}

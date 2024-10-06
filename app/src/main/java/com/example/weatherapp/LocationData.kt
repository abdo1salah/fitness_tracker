package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class Location(private val context: Context) {
    var isGpsEnabled: Boolean = false
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // Function to get the last known location
    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Flow<Location?> = callbackFlow {
        try {
            if (
                hasPermission()
            ) {
                // Permissions are granted, request the location
                // Check if user turn on gps or not
                if (checkGpsStatus()) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            // Do something with the location
                            Log.d("trace", "Location: ${location.latitude}, ${location.longitude}")
                            trySend(location)
                        }
                    }
                } else {
                    trySend(null)
                }
            } else {
                trySend(null)
            }
            awaitClose {
            }
        } catch (e: Exception) {
            Log.d("trace", "Exception in getLocation fun : ${e.message}")
        }
    }

    fun checkGpsStatus(): Boolean {
        val locationManager =
            getSystemService(context, LocationManager::class.java) as LocationManager
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        return isGpsEnabled
    }

    fun hasPermission(): Boolean = ActivityCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
    /*
     private var isDialogShown by mutableStateOf(false)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                false
            ) || permissions.getOrDefault(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                false
            ) -> {
             isDialogShown = false
            }

            else -> {
               isDialogShown = true
                // No location access granted.
            }
        }
    }
     */
    /* @Composable
    fun PermissionDeniedDialog(modifier: Modifier = Modifier, onDialogShown: () -> Unit) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    i.data = Uri.fromParts("package", packageName, null)
                    startActivity(i)
                    onDialogShown()
                }) { Text("Allow") }

            },
            dismissButton = {
                TextButton(onClick = { onDialogShown() }) {
                    Text(text = "Cancel")
                }

            }, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_warning_24),
                    contentDescription = "warning"
                )
            }, title = {
                Text(text = "We need permission to get current location")
            },
            text = {
                Text(text = "Location access is required for accurate weather updates. Please enable location for the app to work correctly.")
            })
    }
*/
}
package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.location.CheckRequirements
import com.example.weatherapp.location.LocationPermissionScreen
import com.example.weatherapp.location.PermissionDeniedDialog
import com.example.weatherapp.location.RequestGpsAlertDialog
import com.example.weatherapp.ui.theme.WeatherAppTheme


class MainActivity : ComponentActivity() {
    lateinit var weatherViewModel: WeatherViewModel
    var isDialogGpsShown: Boolean by mutableStateOf(true)
    var isDialogShown: Boolean by mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            weatherViewModel = viewModel()
            WeatherAppTheme {
                Log.d("trac", "recomposition")
                if (isDialogShown) {
                    PermissionDeniedDialog { isDialogShown = false }
                }
                if (!weatherViewModel.hasPermission) {
                    LocationPermissionScreen(
                        permissionGranted = {
                            isDialogShown = false
                            weatherViewModel.refreshData()
                            weatherViewModel.hasPermission = true
                        },
                        permissionDenied = {
                            isDialogShown = true
                        },
                    )
                } else {
                    if (!weatherViewModel.hasInternetConnection) {
                        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()

                    } else if (!weatherViewModel.hasGps) {
                        if (isDialogGpsShown)
                            RequestGpsAlertDialog(confirmButton = {
                                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                startActivity(intent)
                                isDialogGpsShown = false
                            }, dismissButton = {
                                isDialogGpsShown = false
                            })
                    }
                    Log.d("trace", "response ${weatherViewModel.casheddata.toString()}")
                    if (weatherViewModel.casheddata == null) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    } else
                        HomeScreen(weatherResponse = weatherViewModel.casheddata, hasGps = weatherViewModel.hasGps)
                }
            }

        }
    }
    override fun onRestart() {
        super.onRestart()
        Log.d("trace", "onRestart")
        weatherViewModel.hasGps = CheckRequirements.checkGpsState(this)
    }
}
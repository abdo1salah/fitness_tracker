package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherapp.Api.WeatherCallable
import com.example.weatherapp.Api.WeatherResponse
import com.example.weatherapp.Api.getEndPoint
import com.example.weatherapp.ui.theme.WeatherAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeatherAppTheme {
                //getEndPoint("61.5240","105.3188")
                val weatherViewModel : WeatherViewModel = viewModel()
                val temp = weatherViewModel.weatherData?.current?.temp_c.toString()
                val tempF = weatherViewModel.weatherData?.current?.temp_f.toString()
                val p = weatherViewModel.weatherData?.current?.is_day.toString()
                val p2 = weatherViewModel.weatherData?.current?.feelslike_c.toString()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                    Greeting(
                        name =temp,
                        modifier = Modifier.padding(innerPadding)
                    )
                    Greeting(
                        name =tempF,
                        modifier = Modifier.padding(innerPadding)
                    )
                    Greeting(
                        name =p,
                        modifier = Modifier.padding(innerPadding)
                    )
                    Greeting(
                        name =p2,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                }
            }
        }
    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        WeatherAppTheme {
            Greeting("Android")
        }
    }
}
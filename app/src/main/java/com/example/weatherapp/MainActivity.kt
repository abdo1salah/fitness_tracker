package com.example.weatherapp

import android.os.Bundle
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
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                //getEndPoint("61.5240","105.3188")
                val weatherViewModel : WeatherViewModel = viewModel()
                val temp = weatherViewModel.casheddata?.current?.temp_c.toString()
                val tempF = weatherViewModel.casheddata?.current?.temp_f.toString()
                val p = weatherViewModel.casheddata?.current?.is_day.toString()
                val p2 = weatherViewModel.casheddata?.current?.feelslike_c.toString()
               val f= weatherViewModel.casheddata?.forecast?.forecastday?.get(0)?.day?.avgtemp_c.toString()
                //val alert = weatherViewModel.casheddata?.alerts?.alert?.get(0)?.event
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
                        name =f,
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
            val weatherViewModel: WeatherViewModel = viewModel()
            val temp = weatherViewModel.casheddata?.current?.temp_c.toString()
            val tempF = weatherViewModel.casheddata?.current?.temp_f.toString()
            val p = weatherViewModel.casheddata?.current?.is_day.toString()
            val p2 = weatherViewModel.casheddata?.current?.feelslike_c.toString()
            val f: String = weatherViewModel.casheddata.toString()
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column {
                    Greeting(
                        name = temp,
                        modifier = Modifier.padding(innerPadding)
                    )
                    Greeting(
                        name = tempF,
                        modifier = Modifier.padding(innerPadding)
                    )
                    Greeting(
                        name = p,
                        modifier = Modifier.padding(innerPadding)
                    )
                    Greeting(
                        name = f,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
            }
        }
}
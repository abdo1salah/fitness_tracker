package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.location.CheckRequirements
import com.example.weatherapp.presentation.MainScreen
import com.example.weatherapp.presentation.theme.WeatherAppTheme
import com.example.weatherapp.util.WeatherViewModel

class MainActivity : ComponentActivity() {
    lateinit var weatherViewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        installSplashScreen()

        setContent {

            WeatherAppTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController)
                weatherViewModel = viewModel()
                val sharedPreferences= this.getSharedPreferences("prefs",Context.MODE_PRIVATE)
                weatherViewModel.selectedTempUnit = sharedPreferences.getString("Temperature Unit","Celsius (°C)")!!
                weatherViewModel.selectedWindSpeedUnit = sharedPreferences.getString("Wind Speed Unit","Kilometers (km/h)")!!
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                   // HomeScreen(viewModel= weatherViewModel )
                }

            }
        }
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

            }
        }
}

//package com.example.weatherapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.weatherapp.data.model.Astro
//import com.example.weatherapp.data.model.Day
//import com.example.weatherapp.data.model.Condition
//import com.example.weatherapp.data.model.Forecastday
//import com.example.weatherapp.data.model.Hour
//import com.example.weatherapp.presentation.home.ListTodayWeather
//import com.example.weatherapp.presentation.theme.WeatherAppTheme
//import com.example.weatherapp.util.WeatherViewModel
//
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            WeatherAppTheme {
//                val weatherViewModel : WeatherViewModel = viewModel()
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    // Call the function to test it
//                    WeatherScreen()
//                }
//            }
//        }
//    }
//}
//
//fun getSampleForecastday(): Forecastday {
//    return Forecastday(
//        astro = Astro(
//            is_moon_up = 1,
//            is_sun_up = 0,
//            moon_illumination = 80,
//            moon_phase = "Waxing Gibbous",
//            moonrise = "2024-10-12 18:30",
//            moonset = "2024-10-13 06:00",
//            sunrise = "2024-10-12 05:45",
//            sunset = "2024-10-12 18:30"
//        ),
//        date = "2024-10-12",
//        date_epoch = 1697068800,
//        day = Day(
//            avgtemp_c = 24.0,
//            avghumidity = 60,
//            avgtemp_f = 75.2,
//            avgvis_km = 10.0,
//            avgvis_miles = 6.2,
//            daily_chance_of_rain = 20,
//            daily_chance_of_snow = 0,
//            daily_will_it_rain = 0,
//            daily_will_it_snow = 0,
//            maxtemp_c = 28.0,
//            maxtemp_f = 82.4,
//            maxwind_kph = 15.0,
//            maxwind_mph = 9.3,
//            mintemp_c = 20.0,
//            mintemp_f = 68.0,
//            totalprecip_in = 0.0,
//            totalprecip_mm = 0.0,
//            uv = 5.0,
//            condition = Condition(
//                code = 1000,
//                text = "Sunny",
//                icon = "https://cdn.weatherapi.com/weather/64x64/day/113.png"
//            )
//        ),
//        hour = listOf(
//            Hour(time = "10:00", temp_c = 22.0, condition = Condition( code = 1000,icon = "https://cdn.weatherapi.com/weather/64x64/day/113.png", text = "Sunny")),
//            Hour(time = "11:00", temp_c = 24.0, condition = Condition( code = 1000,icon = "https://cdn.weatherapi.com/weather/64x64/day/113.png", text = "Sunny"))
//        )
//    )
//}
//
//
//
//@Composable
//fun WeatherScreen() {
//    val sampleForecast = getSampleForecastday()
//    val weatherViewModel : WeatherViewModel = viewModel()
//
//    // Display the ListTodayWeather using the sample forecast
//    ListTodayWeather(forecast = sampleForecast)
//}

package com.example.weatherapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.Api.Forecastday
import com.example.weatherapp.Api.WeatherResponse

@Composable
fun HomeScreen(modifier: Modifier = Modifier, weatherResponse: WeatherResponse?,hasGps:Boolean = true) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.weather_wallpaper),
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                weatherResponse?.location?.region.toString(),
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = FontFamily.SansSerif
            )
            Text(
                weatherResponse?.current?.temp_c.toString(),
                fontSize = 100.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                weatherResponse?.current?.condition?.text.toString(),
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.8f),
                fontFamily = FontFamily.SansSerif,

                )
            Icon(
                painter = painterResource(
                   if(hasGps)
                    R.drawable.baseline_location_on_24
                else
                    R.drawable.baseline_location_off_24
                ),
                contentDescription = null
            )
            Image(
                painter = painterResource(R.drawable.sunny),
                contentDescription = null,
                modifier = modifier
                    .padding(8.dp)
                    .size(300.dp)
            )
        ForecastWeather(list = weatherResponse?.forecast?.forecastday?: emptyList())
        }

    }
}

@Composable
fun ForecastWeather(modifier: Modifier = Modifier,list:List<Forecastday>) { LazyRow(horizontalArrangement = Arrangement.SpaceBetween) {
    items(count = list.size ) {
        BlockOfForecastWeather(forecastday = list[it])
    }
    }
}

@Composable
fun BlockOfForecastWeather(modifier: Modifier = Modifier,forecastday: Forecastday) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .size(width = 80.dp, height = 200.dp)
            .wrapContentSize(align = Alignment.Center),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(100.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text("12 PM", color = Color.White, fontSize = 12.sp)
            Image(
                painter = painterResource(R.drawable.cloudy_icon_test),
                contentDescription = null,
                modifier = modifier.size(50.dp)
            )
            Text(
                forecastday.day.avgtemp_c.toString(),
                modifier = modifier.padding(bottom = 4.dp),
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 16.sp
            )
            Text(forecastday.day.condition.text, color = Color.White, textAlign = TextAlign.Center)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun testHomeScreen() {
  HomeScreen(weatherResponse = null)
}
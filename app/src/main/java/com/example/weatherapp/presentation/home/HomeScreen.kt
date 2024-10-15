package com.example.weatherapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.util.WeatherViewModel
import com.example.weatherapp.presentation.theme.MEDIUM_MARGIN
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.presentation.search.ListWeatherForecast
import com.example.weatherapp.presentation.theme.BIG_MARGIN
import com.example.weatherapp.presentation.theme.LARGE_MARGIN
import com.example.weatherapp.presentation.theme.SMALL_MARGIN
import com.example.weatherapp.presentation.theme.VERY_SMALL_MARGIN
import com.example.weatherapp.util.Circle
import com.example.weatherapp.util.LoadingScreen
@Composable
fun HomeScreen(viewModel: WeatherViewModel) {

    val cachedData = viewModel.casheddata
    val location = cachedData?.location

    location?.let {
        val lat = it.lat
        val lon = it.lon



        LaunchedEffect(Unit) {
            viewModel.fetchWeatherDataForLocation(lat.toString(), lon.toString())
        }
    }


    val isLoading = cachedData == null


    LazyColumn(
        modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Current Weather Section
        item {
            when {
                isLoading -> {
                    LoadingScreen()
                }

                cachedData == null -> {
                    ErrorUi()
                }

                cachedData.current == null -> {
                    ErrorUi()
                }

                else -> {
                    // Wrap CurrentWeather with background if needed

                        CurrentWeather(cachedData = cachedData)

                }
            }
        }

        // Hourly Forecast Section
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                val forecastDays = cachedData?.forecast?.forecastday
                val firstForecastDay = forecastDays?.first()

                if (firstForecastDay != null) {
                    // Filter to get every 3rd hour
                    val filteredHours = firstForecastDay.hour.filterIndexed { index, _ -> index % 3 == 0 }

                    items(filteredHours) { hourForecast ->
                        ListTodayWeather(forecast = hourForecast)
                    }
                }
            }

        }

        // Daily Forecast Section
        items(cachedData?.forecast?.forecastday ?: emptyList()) { forecastDay ->
            // Box to ensure background color and spacing for each item
           ListWeatherForecast(
                    date = forecastDay.date,
                    maxTemp = forecastDay.day?.maxtemp_c ?: 0.0,
                    minTemp = forecastDay.day?.mintemp_c ?: 0.0,
                    condition = forecastDay.day?.condition?.text ?: "",
                    iconUrl = forecastDay.day?.condition?.icon ?: ""
                )

        }
    }
}


@Composable
fun CurrentWeather(cachedData: WeatherResponse) {
    val currentWeather = cachedData.current
    val location = cachedData.location?.name ?: ""
    val windSpeed = currentWeather?.wind_kph ?: 0.0
    val visibility = currentWeather?.vis_km ?: 0.0
    val humidity = currentWeather?.humidity ?: 0
    val conditionText = currentWeather?.condition?.text ?: ""
    val feelsLikeTemp = currentWeather?.feelslike_c ?: 0.0
    val temp = currentWeather?.temp_c ?: 0.0
    val date = cachedData.location.localtime
// Construct the image URL properly
    val image = currentWeather?.condition?.icon ?: ""
    val imageUrl = if (image.isNotEmpty()) {
        "https:$image" // Prepend the correct base URL if needed
    } else {
        ""
    }

        ConstraintLayout(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(bottom = MEDIUM_MARGIN)
        ) {

            val (
                icLocation, txtLocation,
                icWind, txtWind, txtVisibility, icVisibility,
                boxHumidityPercentage, txtHumidity, txtDate, coContainer, btnMore
            ) = createRefs()
            //current location
        Text(
            text = location,
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp,
            modifier = Modifier
                .constrainAs(txtLocation) {
                    top.linkTo(parent.top, BIG_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
            //icon
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_location),
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .constrainAs(icLocation) {
                        top.linkTo(txtLocation.top)
                        bottom.linkTo(txtLocation.bottom)
                        end.linkTo(txtLocation.start, SMALL_MARGIN)
                    }
            )
            Column(
                modifier = Modifier
                    .constrainAs(coContainer) {
                        top.linkTo(icLocation.bottom, 50.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colors.onBackground),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


             //temp
                Text(
                    text = "${temp.toInt()}°",
                    fontSize = 90.sp,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colors.primary
                )
                //feels like
                Text(
                    text = "Feels like ${feelsLikeTemp.toInt()}°",
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(top = SMALL_MARGIN)
                )
                // condition
                Text(
                    text = conditionText,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(top = VERY_SMALL_MARGIN)
                )
                if (imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(50.dp)
                    )
                }

            }

      //  Spacer(modifier = Modifier.height(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_wind),
                contentDescription = "",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .constrainAs(icWind) {
                        top.linkTo(coContainer.bottom, LARGE_MARGIN)
                        start.linkTo(parent.start, LARGE_MARGIN)
                    }
            )


//wind speed
            Text(
                text = "$windSpeed km/h",
                fontSize = 16.sp,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .constrainAs(txtWind) {
                        top.linkTo(icWind.top)
                        start.linkTo(icWind.end, SMALL_MARGIN)
                    }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_outline_visibility),
                contentDescription = "",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .constrainAs(icVisibility) {
                        top.linkTo(icWind.bottom, MEDIUM_MARGIN)
                        start.linkTo(parent.start, LARGE_MARGIN)
                    }
            )
//visibility
            Text(
                text = "Visibility $visibility km",
                fontSize = 16.sp,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .constrainAs(txtVisibility) {
                        top.linkTo(icVisibility.top)
                        start.linkTo(icVisibility.end, SMALL_MARGIN)
                    }
            )

            //humidity

            // Humidity circle
            Circle(
                modifier = Modifier
                    .constrainAs(boxHumidityPercentage) {
                        top.linkTo(txtVisibility.bottom, MEDIUM_MARGIN)
                        bottom.linkTo(txtDate.top, MEDIUM_MARGIN)
                        start.linkTo(txtVisibility.end)
                        end.linkTo(parent.end)
                    },

                if (humidity != null) {
                    humidity.toDouble().div(100).toFloat()
                } else {
                    0f
                }, MaterialTheme.colors.secondary
            )


            // Humidity title
            Text(
                text = stringResource(R.string.humidity),
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(txtHumidity) {
                    bottom.linkTo(boxHumidityPercentage.bottom)
                    top.linkTo(boxHumidityPercentage.top)
                    end.linkTo(boxHumidityPercentage.start, MEDIUM_MARGIN)
                }
            )

            //today's date

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary
                        )
                    ) {
                        append("${stringResource(id = R.string.today)}\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 14.sp
                        )
                    ) {
                        append(date)
                    }
                },
                modifier = Modifier
                    .constrainAs(txtDate) {
                        start.linkTo(parent.start, LARGE_MARGIN)
                        top.linkTo(boxHumidityPercentage.bottom)
                    }
            )
        }
}



@Composable
fun ErrorUi() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MEDIUM_MARGIN),
            text = "Something went wrong..",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}
///

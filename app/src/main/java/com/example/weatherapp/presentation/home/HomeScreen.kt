package com.example.weatherapp.presentation.home

import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.network.checkLocationSetting
import com.example.weatherapp.location.CheckRequirements
import com.example.weatherapp.location.LocationData
import com.example.weatherapp.presentation.search.ListWeatherForecast
import com.example.weatherapp.presentation.theme.BIG_MARGIN
import com.example.weatherapp.presentation.theme.LARGE_MARGIN
import com.example.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.example.weatherapp.presentation.theme.SMALL_MARGIN
import com.example.weatherapp.presentation.theme.VERY_SMALL_MARGIN
import com.example.weatherapp.util.Circle
import com.example.weatherapp.util.LoadingScreen
import com.example.weatherapp.util.WeatherViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: WeatherViewModel) {

    val cachedData by viewModel.casheddata.collectAsState(initial = null)
  //  Log.d("trace","in home ${ cachedData!!.location.localtime}")
    var isDialogGpsShown: Boolean by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val locationData = LocationData(context)
    viewModel.hasInternet = CheckRequirements.checkInternetState(context)
    viewModel.hasGps = CheckRequirements.checkGpsState(context)
    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK){
            Log.d("appDebug", "Accepted, ${CheckRequirements.checkGpsState(context)}")
            locationData.startLocationUpdates(viewModel)

        }

        else {
            Log.d("appDebug", "Denied")

        }
    }
    Log.d("trace","recompose")
    if (!viewModel.hasInternet) {
        Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show()
    } else if (!viewModel.hasGps) {
        if (!isDialogGpsShown){
            checkLocationSetting(
                context = context,
                onDisabled = { intentSenderRequest ->
                    settingResultRequest.launch(intentSenderRequest)
                },
                onEnabled = {viewModel.refreshData()}
            )
            isDialogGpsShown = true
        }

    }

    // Directly access cachedData without using collectAsState
   // val cachedData = viewModel.casheddata
    val isLoading = cachedData == null
    Log.d("trace", viewModel.selectedTempUnit)
    // Outer container with background applied to the whole screen
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.refreshData() }
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .pullRefresh(pullRefreshState),
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

                cachedData == null -> {
                    ErrorUi()
                }

                else -> {
                    // Wrap CurrentWeather with background if needed

                    CurrentWeather(cachedData = cachedData!!, viewModel)

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
                    val filteredHours =
                        firstForecastDay.hour.filterIndexed { index, _ -> index % 3 == 0 }

                    items(filteredHours) { hourForecast ->
                        ListTodayWeather(forecast = hourForecast, viewModel)
                    }
                }
            }

        }

        // Daily Forecast Section
        items(cachedData?.forecast?.forecastday ?: emptyList()) { forecastDay ->

            val maxTemp =
                if (viewModel.selectedTempUnit == "Celsius (°C)") forecastDay.day.maxtemp_c else forecastDay.day.maxtemp_f
            val minTemp =
                if (viewModel.selectedTempUnit == "Celsius (°C)") forecastDay.day.mintemp_c else forecastDay.day.mintemp_f
            // Box to ensure background color and spacing for each item
            ListWeatherForecast(
                date = forecastDay.date,
                maxTemp = maxTemp,
                minTemp = minTemp,
                condition = forecastDay.day.condition.text,
                iconUrl = forecastDay.day.condition.icon
            )

        }

    }
    PullRefreshIndicator(
        refreshing = isLoading,
        state = pullRefreshState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
//      backgroundColor = if (viewModel.state.value.isLoading) Color.Red else Color.Green,
    )
}


@Composable
fun CurrentWeather(cachedData: WeatherResponse, viewModel: WeatherViewModel) {
    val currentWeather = cachedData.current
    val location = cachedData.location?.name ?: ""
    val windSpeed =
        if (viewModel.selectedWindSpeedUnit == "Kilometers (km/h)") currentWeather?.wind_kph else currentWeather.wind_mph
    val visibility =
        if (viewModel.selectedWindSpeedUnit == "Kilometers (km/h)") currentWeather?.vis_km else currentWeather.vis_miles
    val humidity = currentWeather?.humidity ?: 0
    val conditionText = currentWeather?.condition?.text ?: ""
    val feelsLikeTemp =
        if (viewModel.selectedTempUnit == "Celsius (°C)") currentWeather?.feelslike_c else currentWeather.feelslike_f
    val temp =
        if (viewModel.selectedTempUnit == "Celsius (°C)") currentWeather?.temp_c else currentWeather.temp_f
    val date = cachedData.location.localtime
    val unitTemp = if (viewModel.selectedTempUnit == "Celsius (°C)") "°C" else "F"
    val unitWindSpeed =
        if (viewModel.selectedWindSpeedUnit == "Kilometers (km/h)") "Km/h" else "Miles/h"
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
            painter = painterResource(id = if (viewModel.hasGps) R.drawable.ic_outline_location else R.drawable.baseline_location_off_24),
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
            text = "$windSpeed $unitWindSpeed",
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
            text = "Visibility $visibility $unitWindSpeed",
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

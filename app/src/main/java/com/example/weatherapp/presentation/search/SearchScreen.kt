package com.example.weatherapp.presentation.search

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.data.local.Constants.C_UNIT
import com.example.weatherapp.data.local.Constants.DEGREE
import com.example.weatherapp.data.local.Constants.DETAILS_SCREEN
import com.example.weatherapp.data.local.Constants.FORMAT_TYPE
import com.example.weatherapp.data.local.Constants.F_UNIT
import com.example.weatherapp.data.local.Constants.IMAGE_URL
import com.example.weatherapp.data.local.Constants.METRIC
import com.example.weatherapp.data.local.Constants.SIZE
import com.example.weatherapp.data.local.Constants.TWELVE_PM
import com.example.weatherapp.data.model.Day
import com.example.weatherapp.data.model.Forecastday
import com.example.weatherapp.presentation.home.ListTodayWeather
//data
//import com.weatherapp.data.model.weather.CurrentWeatherResponse
//import com.weatherapp.presentation.search.ListWeatherForecast

import com.example.weatherapp.presentation.theme.BIG_MARGIN
import com.example.weatherapp.presentation.theme.CUSTOM_MARGIN
import com.example.weatherapp.presentation.theme.LARGE_MARGIN
import com.example.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.example.weatherapp.presentation.theme.SMALL_MARGIN
import com.example.weatherapp.presentation.theme.VERY_SMALL_MARGIN
import com.example.weatherapp.util.Circle
import com.example.weatherapp.util.Line
import com.example.weatherapp.util.LoadingScreen
import com.example.weatherapp.util.RequestState
import com.example.weatherapp.util.formatDate

@Composable
fun SearchScreen() {
    androidx.compose.material3.Text(text = "Search Screen", modifier = Modifier.fillMaxSize())
}


@Composable
fun ListWeatherForecast(
    date: String,
    maxTemp: Double,       // Max temperature for the day
    minTemp: Double,       // Min temperature for the day
    condition: String,     // Condition text
    iconUrl: String        // Icon URL for the condition
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Padding for spacing
    ) {
        val (txtDateTime, imageWeather, txtWeather, txtMaxTemp, txtMinTemp, line) = createRefs()

        // Parse the date to get the day of the week
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = LocalDate.parse(date, formatter)
        val dayOfWeek = parsedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())


        val iconUrlFull = if (iconUrl.isNotEmpty()) {
            "https:$iconUrl"
        } else {
            ""
        }

        // Day of the week (aligned to start)
        Text(
            text = dayOfWeek, // Show day of the week
            fontSize = 14.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.constrainAs(txtDateTime) {
                start.linkTo(parent.start, SMALL_MARGIN)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        // Weather icon (centered)
        Image(
            painter = rememberAsyncImagePainter(model = iconUrlFull),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .constrainAs(imageWeather) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    // Centering the icon
                    start.linkTo(parent.start)
                    end.linkTo(txtMaxTemp.start, SMALL_MARGIN)
                }
        )

        // Weather condition (centered next to icon)
        Text(
            text = condition,
            fontSize = 14.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtWeather) {
                    start.linkTo(imageWeather.end, SMALL_MARGIN)
                    top.linkTo(imageWeather.top)
                    bottom.linkTo(imageWeather.bottom)
                }
                .padding(end = SMALL_MARGIN)
        )

        // Max temperature (aligned to end)
        Text(
            text = "${maxTemp.toInt()}°",
            fontSize = 14.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .constrainAs(txtMaxTemp) {
                    end.linkTo(parent.end, SMALL_MARGIN)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        // Min temperature (aligned next to max temp)
        Text(
            text = "${minTemp.toInt()}°",
            fontSize = 14.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .constrainAs(txtMinTemp) {
                    end.linkTo(txtMaxTemp.start, SMALL_MARGIN)
                    top.linkTo(txtMaxTemp.top)
                    bottom.linkTo(txtMaxTemp.bottom)
                }
        )

        // Divider line
        Line(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(parent.bottom, CUSTOM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
        )
    }
}


/*package com.example.weatherapp.presentation.search

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.data.local.Constants.C_UNIT
import com.example.weatherapp.data.local.Constants.DEGREE
import com.example.weatherapp.data.local.Constants.DETAILS_SCREEN
import com.example.weatherapp.data.local.Constants.FORMAT_TYPE
import com.example.weatherapp.data.local.Constants.F_UNIT
import com.example.weatherapp.data.local.Constants.IMAGE_URL
import com.example.weatherapp.data.local.Constants.METRIC
import com.example.weatherapp.data.local.Constants.SIZE
import com.example.weatherapp.data.local.Constants.TWELVE_PM
import com.example.weatherapp.data.model.Forecastday
import com.example.weatherapp.presentation.home.ListTodayWeather
//data
//import com.weatherapp.data.model.weather.CurrentWeatherResponse
//import com.weatherapp.presentation.search.ListWeatherForecast

import com.example.weatherapp.presentation.theme.BIG_MARGIN
import com.example.weatherapp.presentation.theme.CUSTOM_MARGIN
import com.example.weatherapp.presentation.theme.LARGE_MARGIN
import com.example.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.example.weatherapp.presentation.theme.SMALL_MARGIN
import com.example.weatherapp.presentation.theme.VERY_SMALL_MARGIN
import com.example.weatherapp.util.Circle
import com.example.weatherapp.util.Line
import com.example.weatherapp.util.LoadingScreen
import com.example.weatherapp.util.RequestState
import com.example.weatherapp.util.formatDate
@Composable
fun ListWeatherForecast(
    day: String,
    maxTemp: Double,       // Max temperature for the day
    minTemp: Double,       // Min temperature for the day
    condition: String,     // Condition text
    iconUrl: String        // Icon URL for the condition
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (txtDateTime, imageWeather, txtWeather, txtTemp, line) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_MARGIN),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = day, color = MaterialTheme.colors.primary)
            Text(text = "High: ${maxTemp.toInt()}°C", color = MaterialTheme.colors.secondary)
            Text(text = "Low: ${minTemp.toInt()}°C", color = MaterialTheme.colors.secondaryVariant)
            Text(text = condition, color = MaterialTheme.colors.primaryVariant)
            Image(
                painter = rememberAsyncImagePainter(model = iconUrl),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
*/
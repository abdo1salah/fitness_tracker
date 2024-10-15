package com.example.weatherapp.presentation.search

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.format.TextStyle
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
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
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.presentation.home.ListTodayWeather
import com.example.weatherapp.presentation.theme.BIG_MARGIN
import com.example.weatherapp.presentation.theme.CUSTOM_MARGIN
import com.example.weatherapp.presentation.theme.LARGE_MARGIN
import com.example.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.example.weatherapp.presentation.theme.SMALL_MARGIN
import com.example.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.example.weatherapp.presentation.theme.VERY_SMALL_MARGIN
import com.example.weatherapp.util.Circle
import com.example.weatherapp.util.Line
import com.example.weatherapp.util.LoadingScreen
import com.example.weatherapp.util.RequestState
import com.example.weatherapp.util.WeatherViewModel
import com.example.weatherapp.util.formatDate
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun SearchScreen(viewModel: WeatherViewModel) {
    var searchKeyword by remember { mutableStateOf("") }
    val searchResults = viewModel.searchData
    val cachedData = viewModel.casheddata

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = BIG_MARGIN)
    ) {
        item {
            Header(
                searchKeyword = searchKeyword,
                onTextChange = { newText ->
                    searchKeyword = newText
                    viewModel.updateSearchData(newText)
                },
                onSearchClicked = {
                    if (searchKeyword.isNotEmpty()) {
                        // Fetch weather data for the first result's lat/lon if results exist
                        val firstResult = searchResults.firstOrNull()
                        if (firstResult != null) {
                            val lat = firstResult.lat
                            val lon = firstResult.lon
                            viewModel.fetchWeatherDataForLocation(lat.toString(), lon.toString())
                        } else {

                        }
                    }
                }
            )
        }

        if (searchKeyword.isNotEmpty()) {
            item {
                CurrentWeather(viewModel)
            }

            // Check if cachedData is available before displaying forecasts
            if (cachedData?.forecast?.forecastday.isNullOrEmpty()) {
                item {
                    Text(
                        text = "No weather data available for the entered location.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colors.error
                    )
                }
            } else {
                items(cachedData?.forecast?.forecastday ?: emptyList()) { forecastDay ->
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
    }
}


@Composable
fun CurrentWeather(viewModel: WeatherViewModel) {
    val cachedData = viewModel.casheddata
    when {
        cachedData == null -> {
            LoadingScreen()
        }
        else -> {

            WeatherData(cachedData)
        }
    }
}

@Composable
fun Header(
    searchKeyword: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (txtLabel, txtDescription, search) = createRefs()

        Text(
            text = stringResource(R.string.search),
            fontSize = 25.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .constrainAs(txtLabel) {
                    top.linkTo(parent.top, LARGE_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(R.string.search_caption),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtDescription) {
                    top.linkTo(txtLabel.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
        )

        SearchBar(
            modifier = Modifier
                .constrainAs(search) {
                    top.linkTo(txtDescription.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = searchKeyword,
            onTextChange = { newText -> onTextChange(newText) },
            onSearchClicked = { onSearchClicked(it) }
        )
    }
}



@Composable
fun ListWeatherForecast(
    date: String,
    maxTemp: Double,
    minTemp: Double,
    condition: String,
    iconUrl: String
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


        Text(
            text = dayOfWeek,
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

@ExperimentalCoilApi
@Composable
fun WeatherData(
    cachedData: WeatherResponse
) {

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
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (txtLocation, txtTemp, txtWeather, icWeather, icWind,
            txtWind, txtVisibility, icVisibility,
            boxHumidityPercentage, txtHumidity, spacer) = createRefs()




        Text(
            text = location,
            fontSize = 30.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtLocation) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(parent.top, BIG_MARGIN)
                }
        )
//temp
        Text(
            text = "${temp.toInt()}°",
            fontFamily = FontFamily.Serif,
            fontSize = 90.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .constrainAs(txtTemp) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(txtLocation.bottom, SMALL_MARGIN)
                }
        )
//condition

        Text(
            text = conditionText,
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .constrainAs(txtWeather) {
                    start.linkTo(txtTemp.start, MEDIUM_MARGIN)
                    top.linkTo(txtTemp.bottom, VERY_SMALL_MARGIN)
                }
        )

//icon
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl
            ),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(icWeather) {
                    top.linkTo(txtTemp.top)
                    bottom.linkTo(txtTemp.bottom)
                    start.linkTo(txtTemp.end)
                    end.linkTo(parent.end)
                }
                .size(100.dp)
        )


        Icon(
            painter = painterResource(id = R.drawable.ic_outline_wind),
            contentDescription = "",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .constrainAs(icWind) {
                    top.linkTo(txtWeather.bottom, MEDIUM_MARGIN)
                    start.linkTo(icVisibility.start)
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_outline_visibility),
            contentDescription = "",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .constrainAs(icVisibility) {
                    top.linkTo(icWind.bottom, MEDIUM_MARGIN)
                    end.linkTo(txtVisibility.start, SMALL_MARGIN)
                }
        )

//speed
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


        Text(
            text = "Visibility $visibility km",
            fontSize = 16.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtVisibility) {
                    top.linkTo(icVisibility.top)
                    end.linkTo(parent.end, LARGE_MARGIN)
                }
        )

        Circle(
            modifier = Modifier
                .constrainAs(boxHumidityPercentage) {
                    top.linkTo(icWind.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start, LARGE_MARGIN)
                },
            if (humidity != null) {
                humidity.toDouble().div(100).toFloat()
            } else {
                0f
            },
            MaterialTheme.colors.secondary
        )

        Text(
            text = stringResource(R.string.humidity),
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 16.sp,
            modifier = Modifier.constrainAs(txtHumidity) {
                start.linkTo(boxHumidityPercentage.end, MEDIUM_MARGIN)
                top.linkTo(boxHumidityPercentage.top)
                bottom.linkTo(boxHumidityPercentage.bottom)
            }
        )

        Spacer(
            modifier = Modifier
                .constrainAs(spacer) {
                    top.linkTo(boxHumidityPercentage.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start)
                })

    }
}




@Composable
fun SearchBar(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            onTextChange(it)
        },
        placeholder = {
            Text(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                text = stringResource(id = R.string.search),
                color = MaterialTheme.colors.primary
            )
        },
        textStyle = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colors.primaryVariant),
        singleLine = true,
        leadingIcon = {
            IconButton(modifier = Modifier.alpha(ContentAlpha.disabled),
                onClick = {
                    onSearchClicked(text)
                    focusManager.clearFocus()
                }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = MaterialTheme.colors.primaryVariant,
                    contentDescription = ""
                )
            }
        },
        trailingIcon = {
            IconButton(modifier = Modifier.alpha(ContentAlpha.disabled),
                onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else focusManager.clearFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = MaterialTheme.colors.primaryVariant,
                    contentDescription = ""
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked(text)
                focusManager.clearFocus()
            }
        )
    )
}



package com.example.weatherapp.presentation.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.weatherapp.data.local.Constants.DEGREE
import com.example.weatherapp.data.local.Constants.IMAGE_URL
import com.example.weatherapp.data.local.Constants.SIZE
import com.example.weatherapp.data.model.Forecastday
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.presentation.theme.SMALL_MARGIN
import com.example.weatherapp.util.WeatherViewModel

@ExperimentalCoilApi
@Composable
fun ListTodayWeather(
    forecast: Hour,
    viewModel: WeatherViewModel// Change the type to Hour
) {
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(8.dp)
            .size(60.dp, 110.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        ConstraintLayout {
            val (icWeather, txtTemp, txtTime) = createRefs()
            val image = forecast.condition?.icon ?: ""
            val imageUrl = if (image.isNotEmpty()) {
                "https:$image" // Prepend the correct base URL if needed
            } else {
                ""
            }

            // Display weather icon
            if (imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = "",
                    modifier = Modifier
                        .constrainAs(icWeather) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }.size(40.dp)
                )
            }

            // Display temperature
            Text(
                text = "${if(viewModel.selectedTempUnit == "Celsius (°C)") forecast.temp_c.toInt() else forecast.temp_f.toInt() } $DEGREE", // Access temp from hour
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 16.sp,
                modifier = Modifier
                    .constrainAs(txtTemp) {
                        top.linkTo(icWeather.bottom, SMALL_MARGIN)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Display time
            Text(
                text = forecast.time.split(" ")[1], // Use only the time part
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 14.sp,
                modifier = Modifier
                    .constrainAs(txtTime) {
                        top.linkTo(txtTemp.bottom, SMALL_MARGIN)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}


//
//@ExperimentalCoilApi
//@Composable
//fun ListTodayWeather(
//    viewModel: WeatherViewModel
//) {
//    // Access the cachedData from the ViewModel
//    val cachedData by remember { derivedStateOf { viewModel.casheddata } }
//
//    // Assuming cachedData is a WeatherResponse and contains the forecast data
//    val forecast = cachedData?.forecast?.forecastday?.firstOrNull() // Get the first forecast day
//
//    if (forecast != null) {
//        Card(
//            shape = RoundedCornerShape(30.dp),
//            elevation = 10.dp,
//            modifier = Modifier
//                .padding(8.dp)
//                .size(60.dp, 110.dp),
//            backgroundColor = MaterialTheme.colors.surface
//        ) {
//            ConstraintLayout {
//                val (icWeather, txtTemp, txtTime) = createRefs()
//
//                // Use cachedData to get the condition icon
//                Image(
//                    painter = rememberAsyncImagePainter(
//                        model = "${IMAGE_URL}${forecast.day.condition.icon}${SIZE}"
//                    ),
//                    contentDescription = "",
//                    modifier = Modifier
//                        .constrainAs(icWeather) {
//                            top.linkTo(parent.top)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        }
//                        .size(40.dp)
//                )
//
//                // Use cachedData to display the average temperature
//                Text(
//                    text = "${forecast.day.avgtemp_c.toInt()} $DEGREE",
//                    color = MaterialTheme.colors.primaryVariant,
//                    fontSize = 16.sp,
//                    modifier = Modifier
//                        .constrainAs(txtTemp) {
//                            top.linkTo(icWeather.bottom, SMALL_MARGIN)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        }
//                )
//
//                // Display time for the first hour
//                val firstHour = forecast.hour.firstOrNull() // Get the first hour from cached data
//                Text(
//                    text = firstHour?.time ?: "", // Safely access the time
//                    color = MaterialTheme.colors.primaryVariant,
//                    fontSize = 14.sp,
//                    modifier = Modifier
//                        .constrainAs(txtTime) {
//                            top.linkTo(txtTemp.bottom, SMALL_MARGIN)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        }
//                )
//            }
//        }
//    }
//}

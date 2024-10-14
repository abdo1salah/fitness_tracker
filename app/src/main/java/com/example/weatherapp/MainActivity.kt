package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                SettingsScreen()
            }
        }
                /*val viewModel : WeatherViewModel = viewModel()
                //getEndPoint("61.5240","105.3188")
                //val alert = weatherViewModel.casheddata?.alerts?.alert?.get(0)?.event
                var search by remember {
                    mutableStateOf("")
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier
                        .padding(innerPadding)) {
                        OutlinedTextField(
                            value = search,
                            textStyle = TextStyle.Default.copy(fontSize = 22.sp),
                            onValueChange = {
                                search = it
                                viewModel.updateSearchData(it)
                            },
                            label = {
                                Text(text = "Title")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier
                                .padding(top = 52.dp)
                                .fillMaxWidth())
                        LazyColumn {
                            val items = viewModel.searchData
                            items(items) { item ->
                                Text(text =item.name )
                            }
                        }
                }
                }
            }
        }*/
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
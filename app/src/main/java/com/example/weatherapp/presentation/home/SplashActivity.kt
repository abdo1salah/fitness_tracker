package com.example.weatherapp.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.presentation.theme.WeatherAppTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Disable the default splash screen
        installSplashScreen().setKeepOnScreenCondition { false }

        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                SplashScreen()
            }
        }
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    private fun SplashScreen() {
        val alpha = remember { Animatable(0f) }

        LaunchedEffect(key1 = true) {
            alpha.animateTo(1f, animationSpec = tween(1500))
            delay(2000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFF121418)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.preview_cloudy),
                contentDescription = null,
                modifier = Modifier.size(150.dp).alpha(alpha.value)
            )
        }
    }
}

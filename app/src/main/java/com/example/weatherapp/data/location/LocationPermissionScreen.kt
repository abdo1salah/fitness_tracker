package com.example.weatherapp.location

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R

@Composable
fun LocationPermissionScreen(
    modifier: Modifier = Modifier,
    permissionGranted: () -> Unit,
    permissionDenied: () -> Unit
) {
    val locationPermissionsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) || permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> permissionGranted()

                else -> permissionDenied()
            }
        }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.location_permission_screen),
            contentDescription = null,
            modifier = modifier.size(300.dp)
        )
        ElevatedButton(onClick = {
            locationPermissionsLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }) {
            Text("Get Permission")
        }
    }
}

@Composable
fun PermissionDeniedDialog(modifier: Modifier = Modifier, onDialogShown: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = {
                val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                i.data = Uri.fromParts("package", context.packageName, null)
                context.startActivity(i)
                onDialogShown()
            }) { Text("Allow") }

        },
        dismissButton = {
            TextButton(onClick = { onDialogShown() }) {
                Text(text = "Cancel")

            }

        }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_warning_24),
                contentDescription = "warning"
            )
        }, title = {
            Text(text = "We need permission to get current location")
        },
        text = {
            Text(text = "LocationData access is required for accurate weather updates. Please enable location for the app to work correctly.")
        })
}

@Preview(showSystemUi = true)
@Composable
fun TestScreen() {
    LocationPermissionScreen(permissionGranted = {}){}
}
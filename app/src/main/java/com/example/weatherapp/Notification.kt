package com.example.weatherapp

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun permissionDenied(onDialogShown: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var showDialog by remember { mutableStateOf(true) }

    // Observe lifecycle events
    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_RESUME) {
                    // Check notification permission on resume
                    if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                        showDialog = false // Hide the dialog if permission is granted
                    }
                }
            }
        })
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Dismiss on back press
            confirmButton = {
                TextButton(onClick = {
                    // Open app settings
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text(text = "Allow")
                }
            },
            dismissButton = {
                TextButton(onClick = onDialogShown) {
                    Text(text = "Cancel")
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_warning_24),
                    contentDescription = "Warning",
                    tint = Color.Unspecified
                )
            },
            title = {
                Text(text = "We need permission to send notifications")
            },
            text = { Text(text = "Please allow notifications to receive weather updates.") }
           )
       }
}
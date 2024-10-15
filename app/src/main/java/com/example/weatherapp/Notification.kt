package com.example.weatherapp

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun permissionDenied(onDialogShown: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", `package`, null)
                    }
                context.startActivity(intent)
                onDialogShown()
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
                painter = painterResource(id = R.drawable.ic_launcher_background),
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

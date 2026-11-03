package de.lichtblickraum.app.ui.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.lichtblickraum.core.config.AppLinks

fun openInBrowser(context: Context, url: String) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Es wurde kein Browser gefunden.", Toast.LENGTH_LONG).show()
    }
}

/** "Termin buchen": öffnet den Calendly-Link – oder erklärt freundlich, dass er noch fehlt. */
@Composable
fun BookingButton(modifier: Modifier = Modifier, text: String = "Termin buchen") {
    val context = LocalContext.current
    var showMissing by remember { mutableStateOf(false) }
    PrimaryButton(
        text = text,
        modifier = modifier,
        onClick = {
            if (AppLinks.isBookingConfigured) { openInBrowser(context, AppLinks.BOOKING_URL) } else { showMissing = true }
        },
    )
    if (showMissing) {
        AlertDialog(
            onDismissRequest = { showMissing = false },
            title = { Text("Terminbuchung folgt") },
            text = {
                Text(
                    "Die Online-Terminbuchung wird gerade eingerichtet. " +
                        "Bis dahin findest du Kontaktmöglichkeiten auf der Website von Lichtblick.",
                )
            },
            confirmButton = {
                TextButton(onClick = { showMissing = false; openInBrowser(context, AppLinks.WEBSITE_URL) }) {
                    Text("Zur Website")
                }
            },
            dismissButton = { TextButton(onClick = { showMissing = false }) { Text("Schließen") } },
        )
    }
}

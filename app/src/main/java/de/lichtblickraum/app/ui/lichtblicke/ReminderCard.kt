package de.lichtblickraum.app.ui.lichtblicke

import android.Manifest
import android.app.TimePickerDialog
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.reminders.ReminderWorker
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.SecondaryButton
import de.lichtblickraum.app.ui.components.TouchHeight
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.reminders.ReminderSettings
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

private fun LocalTime.label() = "%02d:%02d Uhr".format(hour, minute)
private fun DayOfWeek.label() = getDisplayName(TextStyle.FULL, Locale.GERMAN)

/** Freiwillige Erinnerungen: täglich und wöchentlich, mit selbst gewählter Zeit. */
@Composable
fun ReminderCard(container: AppContainer) {
    val context = LocalContext.current
    val settings by container.reminderStore.settings.collectAsStateWithLifecycle()
    var pending by remember { mutableStateOf<ReminderSettings?>(null) }
    var showDenied by remember { mutableStateOf(false) }
    var pickDay by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        val wanted = pending
        pending = null
        if (granted && wanted != null) { container.setReminders(wanted) } else { showDenied = true }
    }

    fun turnOn(newSettings: ReminderSettings) {
        if (ReminderWorker.canNotify(context)) {
            container.setReminders(newSettings)
        } else if (Build.VERSION.SDK_INT >= 33) {
            pending = newSettings
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            showDenied = true
        }
    }

    fun pickTime(current: LocalTime, onPicked: (LocalTime) -> Unit) {
        TimePickerDialog(context, { _, h, m -> onPicked(LocalTime.of(h, m)) }, current.hour, current.minute, true).show()
    }

    LichtblickCard {
        Text("Sanfte Erinnerungen", style = MaterialTheme.typography.titleLarge)
        Text(
            "Freiwillig. Eine Erinnerung kommt nur, wenn dein Impuls noch offen ist – " +
                "und nie während einer Pause.",
            style = MaterialTheme.typography.bodyMedium,
            color = LichtblickColors.TextMuted,
        )

        ReminderSwitch(
            label = "Täglich an deinen Lichtblick erinnern",
            checked = settings.dailyEnabled,
            onChange = { on ->
                val s = settings.copy(dailyEnabled = on)
                if (on) turnOn(s) else container.setReminders(s)
            },
        )
        if (settings.dailyEnabled) {
            SecondaryButton("Uhrzeit: ${settings.dailyTime.label()}", {
                pickTime(settings.dailyTime) { container.setReminders(settings.copy(dailyTime = it)) }
            })
        }

        ReminderSwitch(
            label = "Wöchentlich an deinen Wochenimpuls erinnern",
            checked = settings.weeklyEnabled,
            onChange = { on ->
                val s = settings.copy(weeklyEnabled = on)
                if (on) turnOn(s) else container.setReminders(s)
            },
        )
        if (settings.weeklyEnabled) {
            SecondaryButton("Tag: ${settings.weeklyDay.label()}", { pickDay = true })
            SecondaryButton("Uhrzeit: ${settings.weeklyTime.label()}", {
                pickTime(settings.weeklyTime) { container.setReminders(settings.copy(weeklyTime = it)) }
            })
        }
    }

    if (pickDay) {
        AlertDialog(
            onDismissRequest = { pickDay = false },
            title = { Text("An welchem Tag?") },
            text = {
                Column {
                    DayOfWeek.entries.forEach { day ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .heightIn(min = TouchHeight)
                                .selectable(selected = day == settings.weeklyDay, role = Role.RadioButton, onClick = {
                                    container.setReminders(settings.copy(weeklyDay = day))
                                    pickDay = false
                                }),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = day == settings.weeklyDay,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(selectedColor = LichtblickColors.GoldDark),
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(day.label(), style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { pickDay = false }) { Text("Schließen") } },
        )
    }

    if (showDenied) {
        AlertDialog(
            onDismissRequest = { showDenied = false },
            title = { Text("Benachrichtigungen sind aus") },
            text = {
                Text(
                    "Damit Erinnerungen erscheinen können, braucht Lichtblick die Erlaubnis für " +
                        "Benachrichtigungen. Du kannst sie in den Android-Einstellungen unter " +
                        "Apps → Lichtblick → Benachrichtigungen erlauben – wenn du magst.",
                )
            },
            confirmButton = { TextButton(onClick = { showDenied = false }) { Text("Verstanden") } },
        )
    }
}

@Composable
private fun ReminderSwitch(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .heightIn(min = TouchHeight)
            .toggleable(value = checked, role = Role.Switch, onValueChange = onChange),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        Spacer(Modifier.width(12.dp))
        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = SwitchDefaults.colors(
                checkedTrackColor = LichtblickColors.GoldDark,
                uncheckedBorderColor = LichtblickColors.Gold,
                uncheckedThumbColor = LichtblickColors.Gold,
            ),
        )
    }
}

package de.lichtblickraum.app.reminders

import android.content.Context
import de.lichtblickraum.core.reminders.ReminderSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.DayOfWeek
import java.time.LocalTime

/** Speichert die Erinnerungs-Einstellungen auf dem Gerät. */
class ReminderStore(context: Context) {
    private val prefs = context.getSharedPreferences("erinnerungen", Context.MODE_PRIVATE)
    private val defaults = ReminderSettings()

    private val _settings = MutableStateFlow(load())
    val settings: StateFlow<ReminderSettings> = _settings.asStateFlow()

    fun save(settings: ReminderSettings) {
        prefs.edit()
            .putBoolean("taeglich", settings.dailyEnabled)
            .putInt("taeglich_minute", settings.dailyTime.toSecondOfDay() / 60)
            .putBoolean("woechentlich", settings.weeklyEnabled)
            .putInt("woechentlich_tag", settings.weeklyDay.value)
            .putInt("woechentlich_minute", settings.weeklyTime.toSecondOfDay() / 60)
            .apply()
        _settings.value = settings
    }

    private fun load(): ReminderSettings = try {
        ReminderSettings(
            dailyEnabled = prefs.getBoolean("taeglich", defaults.dailyEnabled),
            dailyTime = minutes(prefs.getInt("taeglich_minute", defaults.dailyTime.toSecondOfDay() / 60)),
            weeklyEnabled = prefs.getBoolean("woechentlich", defaults.weeklyEnabled),
            weeklyDay = DayOfWeek.of(prefs.getInt("woechentlich_tag", defaults.weeklyDay.value)),
            weeklyTime = minutes(prefs.getInt("woechentlich_minute", defaults.weeklyTime.toSecondOfDay() / 60)),
        )
    } catch (e: Exception) {
        defaults
    }

    private fun minutes(m: Int): LocalTime = LocalTime.of((m / 60) % 24, m % 60)
}

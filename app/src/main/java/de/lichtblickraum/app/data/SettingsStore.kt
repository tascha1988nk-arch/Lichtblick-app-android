package de.lichtblickraum.app.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Kleine Einstellungen auf dem Gerät (keine Inhalte):
 * - ob Rückschau-Einträge gespeichert werden dürfen
 * - ob die kleinen Lichtblicke pausiert sind
 */
class SettingsStore(context: Context) {
    private val prefs = context.getSharedPreferences("einstellungen", Context.MODE_PRIVATE)

    private val _saveEntries = MutableStateFlow(prefs.getBoolean(KEY_SAVE, false))
    val saveEntries: StateFlow<Boolean> = _saveEntries.asStateFlow()

    private val _impulsesPaused = MutableStateFlow(prefs.getBoolean(KEY_PAUSED, false))
    val impulsesPaused: StateFlow<Boolean> = _impulsesPaused.asStateFlow()

    fun setSaveEntries(value: Boolean) {
        prefs.edit().putBoolean(KEY_SAVE, value).apply()
        _saveEntries.value = value
    }

    fun setImpulsesPaused(value: Boolean) {
        prefs.edit().putBoolean(KEY_PAUSED, value).apply()
        _impulsesPaused.value = value
    }

    private companion object {
        const val KEY_SAVE = "rueckschau_speichern"
        const val KEY_PAUSED = "lichtblicke_pausiert"
    }
}

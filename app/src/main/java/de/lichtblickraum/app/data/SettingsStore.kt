package de.lichtblickraum.app.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

/**
 * Kleine Einstellungen auf dem Gerät (keine Inhalte):
 * - ob Rückschau-Einträge gespeichert werden dürfen
 * - ob die kleinen Lichtblicke pausiert sind
 */
class SettingsStore(context: Context) {
    private val prefs = context.getSharedPreferences("einstellungen", Context.MODE_PRIVATE)

    // Standard: Speichern ist eingeschaltet (nur auf dem Gerät). Wer es ausschaltet, behält diese Wahl.
    private val _saveEntries = MutableStateFlow(prefs.getBoolean(KEY_SAVE, true))
    val saveEntries: StateFlow<Boolean> = _saveEntries.asStateFlow()

    private val _impulsesPaused = MutableStateFlow(prefs.getBoolean(KEY_PAUSED, false))
    val impulsesPaused: StateFlow<Boolean> = _impulsesPaused.asStateFlow()

    /** Bis zu diesem Tag (einschließlich) wird der Hilfe-Hinweis nicht gezeigt. */
    private val _supportHiddenUntil = MutableStateFlow(
        prefs.getLong(KEY_SUPPORT_HIDDEN, Long.MIN_VALUE).let { if (it == Long.MIN_VALUE) null else LocalDate.ofEpochDay(it) },
    )
    val supportHiddenUntil: StateFlow<LocalDate?> = _supportHiddenUntil.asStateFlow()

    fun hideSupportUntil(date: LocalDate) {
        prefs.edit().putLong(KEY_SUPPORT_HIDDEN, date.toEpochDay()).apply()
        _supportHiddenUntil.value = date
    }

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
        const val KEY_SUPPORT_HIDDEN = "hilfe_hinweis_aus_bis"
    }
}

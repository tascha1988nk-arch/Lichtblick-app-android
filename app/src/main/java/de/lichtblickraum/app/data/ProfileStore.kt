package de.lichtblickraum.app.data

import android.content.Context
import de.lichtblickraum.core.profile.Focus
import de.lichtblickraum.core.profile.TimePreference
import de.lichtblickraum.core.profile.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

/** Antworten aus den Eingangsfragen – nur auf dem Gerät. */
class ProfileStore(context: Context) {
    private val prefs = context.getSharedPreferences("profil", Context.MODE_PRIVATE)

    private val _profile = MutableStateFlow(load())
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    fun save(profile: UserProfile) {
        prefs.edit()
            .putBoolean("fertig", profile.onboardingDone)
            .putStringSet("themen", profile.focus.map { it.name }.toSet())
            .putString("wunsch", profile.wish)
            .putString("zeit", profile.time.name)
            .putLong("rueckblick_basis", profile.reviewInviteBase?.toEpochDay() ?: Long.MIN_VALUE)
            .apply()
        _profile.value = profile
    }

    private fun load(): UserProfile = try {
        UserProfile(
            onboardingDone = prefs.getBoolean("fertig", false),
            focus = prefs.getStringSet("themen", emptySet()).orEmpty()
                .mapNotNull { n -> Focus.entries.firstOrNull { it.name == n } }.toSet(),
            wish = prefs.getString("wunsch", null)?.takeIf { it.isNotBlank() },
            time = TimePreference.entries.firstOrNull { it.name == prefs.getString("zeit", null) } ?: TimePreference.SHORT,
            reviewInviteBase = prefs.getLong("rueckblick_basis", Long.MIN_VALUE)
                .let { if (it == Long.MIN_VALUE) null else LocalDate.ofEpochDay(it) },
        )
    } catch (e: Exception) {
        UserProfile()
    }
}

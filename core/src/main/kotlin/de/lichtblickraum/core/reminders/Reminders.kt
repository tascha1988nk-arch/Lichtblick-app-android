package de.lichtblickraum.core.reminders

import java.time.DayOfWeek
import java.time.LocalTime

/**
 * VORBEREITUNG für spätere, freiwillige Erinnerungen. In Version 1 NICHT aktiv.
 *
 * Geplant: Nutzer schalten Erinnerungen selbst ein und wählen die Uhrzeit.
 * Umsetzung später in der App z. B. mit WorkManager/AlarmManager und der
 * Android-Berechtigung POST_NOTIFICATIONS (Abfrage erst beim Einschalten).
 * Erinnerungstexte sollen einladend sein und nie mahnen.
 */
data class ReminderSettings(
    val dailyEnabled: Boolean = false,
    val dailyTime: LocalTime = LocalTime.of(9, 0),
    val weeklyEnabled: Boolean = false,
    val weeklyDay: DayOfWeek = DayOfWeek.SUNDAY,
    val weeklyTime: LocalTime = LocalTime.of(18, 0),
)

interface ReminderScheduler {
    fun apply(settings: ReminderSettings)
}

/** Version 1: Es werden keine Erinnerungen geplant. */
object NoReminderScheduler : ReminderScheduler {
    override fun apply(settings: ReminderSettings) = Unit
}

/** Mögliche spätere Erinnerungstexte – freundlich, ohne Druck. */
object ReminderTexts {
    const val DAILY = "Ein kleiner Lichtblick wartet auf dich – wenn du magst."
    const val WEEKLY = "Dein Wochenimpuls ist da. Ganz ohne Eile."
}

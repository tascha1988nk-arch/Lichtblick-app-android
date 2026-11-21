package de.lichtblickraum.core.reminders

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

/**
 * Freiwillige Erinnerungen an die kleinen Lichtblicke.
 *
 * - Standardmäßig AUS; Nutzer schalten sie selbst ein und wählen die Uhrzeit.
 * - Eine Erinnerung erscheint nur, wenn der Impuls noch offen ist und nicht pausiert wurde.
 * - Texte laden ein und mahnen nie.
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

/** Plant nichts (z. B. für Tests). */
object NoReminderScheduler : ReminderScheduler {
    override fun apply(settings: ReminderSettings) = Unit
}

/** Erinnerungstexte – freundlich, ohne Druck. */
object ReminderTexts {
    const val TITLE = "Kleine Lichtblicke"
    const val DAILY = "Ein kleiner Lichtblick wartet auf dich – wenn du magst."
    const val WEEKLY = "Dein Wochenimpuls ist da. Ganz ohne Eile."
    const val CHANNEL_NAME = "Erinnerungen an kleine Lichtblicke"
    const val CHANNEL_DESCRIPTION = "Freiwillige, sanfte Erinnerungen zu der Uhrzeit, die du gewählt hast."
}

/** Berechnet den nächsten Erinnerungszeitpunkt (in der Zeitzone von [now], inkl. Sommerzeit). */
object ReminderTiming {

    fun nextDaily(now: ZonedDateTime, time: LocalTime): ZonedDateTime {
        val today = now.with(time).withSecond(0).withNano(0)
        return if (today.isAfter(now)) today else today.plusDays(1).with(time)
    }

    fun nextWeekly(now: ZonedDateTime, day: DayOfWeek, time: LocalTime): ZonedDateTime {
        val candidate = now.with(TemporalAdjusters.nextOrSame(day)).with(time).withSecond(0).withNano(0)
        return if (candidate.isAfter(now)) candidate else candidate.plusWeeks(1).with(time)
    }
}

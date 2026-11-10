package de.lichtblickraum.core

import de.lichtblickraum.core.reminders.ReminderSettings
import de.lichtblickraum.core.reminders.ReminderTiming
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

class ReminderTest {
    private val berlin = ZoneId.of("Europe/Berlin")
    private fun at(y: Int, m: Int, d: Int, h: Int, min: Int) = ZonedDateTime.of(LocalDateTime.of(y, m, d, h, min), berlin)

    @Test
    fun remindersAreOffByDefault() {
        val s = ReminderSettings()
        assertFalse(s.dailyEnabled)
        assertFalse(s.weeklyEnabled)
    }

    @Test
    fun dailyLaterTodayOrTomorrow() {
        val nine = LocalTime.of(9, 0)
        assertEquals(at(2026, 11, 10, 9, 0), ReminderTiming.nextDaily(at(2026, 11, 10, 7, 30), nine))
        assertEquals(at(2026, 11, 11, 9, 0), ReminderTiming.nextDaily(at(2026, 11, 10, 9, 0), nine))
        assertEquals(at(2026, 11, 11, 9, 0), ReminderTiming.nextDaily(at(2026, 11, 10, 20, 0), nine))
    }

    @Test
    fun dailyKeepsLocalTimeAcrossDaylightSaving() {
        // Umstellung auf Sommerzeit am 29.03.2026
        val next = ReminderTiming.nextDaily(at(2026, 3, 28, 22, 0), LocalTime.of(9, 0))
        assertEquals(LocalTime.of(9, 0), next.toLocalTime())
        assertEquals(29, next.dayOfMonth)
    }

    @Test
    fun weeklyNextChosenDay() {
        val six = LocalTime.of(18, 0)
        // Dienstag, 10.11.2026 -> Sonntag, 15.11.2026
        assertEquals(at(2026, 11, 15, 18, 0), ReminderTiming.nextWeekly(at(2026, 11, 10, 12, 0), DayOfWeek.SUNDAY, six))
        // Sonntag vor 18 Uhr -> derselbe Tag
        assertEquals(at(2026, 11, 15, 18, 0), ReminderTiming.nextWeekly(at(2026, 11, 15, 10, 0), DayOfWeek.SUNDAY, six))
        // Sonntag nach 18 Uhr -> nächste Woche
        assertEquals(at(2026, 11, 22, 18, 0), ReminderTiming.nextWeekly(at(2026, 11, 15, 18, 30), DayOfWeek.SUNDAY, six))
    }
}

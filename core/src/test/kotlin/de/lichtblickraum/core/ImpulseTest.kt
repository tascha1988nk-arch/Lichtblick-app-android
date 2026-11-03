package de.lichtblickraum.core

import de.lichtblickraum.core.impulses.DailyImpulses
import de.lichtblickraum.core.impulses.ImpulseCadence
import de.lichtblickraum.core.impulses.ImpulsePlanner
import de.lichtblickraum.core.impulses.ImpulseStatus
import de.lichtblickraum.core.impulses.LichtblickeController
import de.lichtblickraum.core.impulses.WeeklyImpulses
import de.lichtblickraum.core.journal.InMemoryJournalStore
import de.lichtblickraum.core.journal.Journal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class ImpulseTest {

    private val planner = ImpulsePlanner()
    private val monday = LocalDate.of(2026, 11, 2)

    @Test
    fun enoughVariedImpulses() {
        assertTrue(DailyImpulses.all.size >= 20)
        assertTrue(WeeklyImpulses.all.size >= 8)
        val all = DailyImpulses.all + WeeklyImpulses.all
        assertEquals("IDs eindeutig", all.size, all.map { it.id }.toSet().size)
        assertTrue(DailyImpulses.all.all { it.cadence == ImpulseCadence.DAILY })
        assertTrue(WeeklyImpulses.all.all { it.cadence == ImpulseCadence.WEEKLY })
        assertTrue("alle Themen vertreten", DailyImpulses.all.map { it.theme }.toSet().size >= 5)
        all.forEach { assertTrue(it.task.isNotBlank() && it.explanation.isNotBlank()) }
    }

    @Test
    fun sameDaySameImpulse_nextDayDifferent() {
        assertEquals(planner.dailyFor(monday), planner.dailyFor(monday))
        assertNotEquals(planner.dailyFor(monday), planner.dailyFor(monday.plusDays(1)))
    }

    @Test
    fun weeklyStableWithinWeek() {
        val w = planner.weeklyFor(monday)
        (0L..6L).forEach { assertEquals(w, planner.weeklyFor(monday.plusDays(it))) }
        assertNotEquals(w, planner.weeklyFor(monday.plusDays(7)))
    }

    @Test
    fun swappingReachesEveryImpulse() {
        val seenDaily = (0 until DailyImpulses.all.size).map { planner.dailyFor(monday, it).id }.toSet()
        assertEquals(DailyImpulses.all.size, seenDaily.size)
        val seenWeekly = (0 until WeeklyImpulses.all.size).map { planner.weeklyFor(monday, it).id }.toSet()
        assertEquals(WeeklyImpulses.all.size, seenWeekly.size)
        assertNotEquals(planner.dailyFor(monday, 0), planner.dailyFor(monday, 1))
    }

    @Test
    fun everyDayOfAYearYieldsAnImpulse() {
        (0L..400L).forEach { assertNotNull(planner.dailyFor(LocalDate.of(1970, 1, 1).plusDays(it - 200))) }
    }

    @Test
    fun controllerSkipSwapCompleteAndReopen() {
        val journal = Journal(InMemoryJournalStore(), persistent = false)
        val c = LichtblickeController(planner, journal)
        fun state(day: LocalDate = monday) = c.state(day, c.choices.value, journal.entries.value)

        val first = state().today.impulse
        assertEquals(ImpulseStatus.OPEN, state().today.status)

        c.swapDaily(monday)
        assertNotEquals(first, state().today.impulse)

        c.skipDaily(monday)
        assertEquals(ImpulseStatus.SKIPPED, state().today.status)
        assertEquals("Auslassen gilt nur für heute", ImpulseStatus.OPEN, state(monday.plusDays(1)).today.status)
        c.reopenDaily(monday)
        assertEquals(ImpulseStatus.OPEN, state().today.status)

        // Abschließen ohne Notiz ist möglich
        val impulse = state().today.impulse
        val entry = c.completeDaily(monday, impulse, "   ")
        assertNull(entry.note)
        assertEquals(ImpulseStatus.DONE, state().today.status)
        assertNotNull(state().today.feedback)

        // Wochenimpuls an einem anderen Tag derselben Woche erledigt
        c.completeWeekly(monday.plusDays(3), state().week.impulse, "Notiz")
        assertEquals(ImpulseStatus.DONE, state(monday.plusDays(6)).week.status)
        assertEquals(ImpulseStatus.OPEN, state(monday.plusDays(7)).week.status)

        // Nach einer Pause (Tage ohne Nutzung) gibt es keinen Rückstand – einfach ein offener Impuls
        assertEquals(ImpulseStatus.OPEN, state(monday.plusDays(30)).today.status)
    }
}

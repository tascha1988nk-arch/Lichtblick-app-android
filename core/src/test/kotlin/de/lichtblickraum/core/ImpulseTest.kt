package de.lichtblickraum.core

import de.lichtblickraum.core.impulses.DailyImpulses
import de.lichtblickraum.core.impulses.ImpulseCadence
import de.lichtblickraum.core.impulses.ImpulsePlanner
import de.lichtblickraum.core.impulses.ImpulseTheme
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
        assertEquals(60, DailyImpulses.all.size)
        assertTrue(WeeklyImpulses.all.size >= 8)
        val all = DailyImpulses.all + WeeklyImpulses.all
        assertEquals("IDs eindeutig", all.size, all.map { it.id }.toSet().size)
        assertTrue(DailyImpulses.all.all { it.cadence == ImpulseCadence.DAILY })
        assertTrue(WeeklyImpulses.all.all { it.cadence == ImpulseCadence.WEEKLY })
        assertEquals("alle Themen vertreten", ImpulseTheme.entries.toSet(), DailyImpulses.all.map { it.theme }.toSet())
        assertEquals("Titel eindeutig", DailyImpulses.all.size, DailyImpulses.all.map { it.title }.toSet().size)
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

    private fun dailySequence(from: LocalDate, days: Int) = (0 until days).map { planner.dailyFor(from.plusDays(it.toLong())).id }

    @Test
    fun everyRoundContainsEachImpulseOnce_inNewOrder() {
        val n = DailyImpulses.all.size
        val roundStart = LocalDate.ofEpochDay(Math.floorDiv(monday.toEpochDay(), n.toLong()) * n)
        val r1 = dailySequence(roundStart, n)
        val r2 = dailySequence(roundStart.plusDays(n.toLong()), n)
        val r3 = dailySequence(roundStart.plusDays(2L * n), n)
        listOf(r1, r2, r3).forEach { assertEquals("jeder Impuls genau einmal pro Runde", n, it.toSet().size) }
        assertNotEquals("neue Reihenfolge in jeder Runde", r1, r2)
        assertNotEquals(r2, r3)
    }

    @Test
    fun noShortRepeatsAcrossRoundBoundaries() {
        val n = DailyImpulses.all.size
        val window = minOf(10, n / 3) + 1
        val seq = dailySequence(LocalDate.of(2026, 1, 1), 2000)
        seq.windowed(window).forEachIndexed { i, w ->
            assertEquals("Wiederholung innerhalb von $window Tagen ab Tag $i", w.size, w.toSet().size)
        }
        val weekly = (0 until 300).map { planner.weeklyFor(LocalDate.of(2026, 1, 5).plusWeeks(it.toLong())).id }
        val wWindow = WeeklyImpulses.all.size / 4 + 1
        weekly.windowed(wWindow).forEach { assertEquals(it.size, it.toSet().size) }
    }

    @Test
    fun recentlyDoneImpulseIsSkipped() {
        (0L..120L).forEach { d ->
            val day = monday.plusDays(d)
            val usual = planner.dailyFor(day)
            val other = planner.dailyFor(day, recentlyDone = setOf(usual.id))
            assertNotEquals(usual.id, other.id)
            val week = planner.weeklyFor(day)
            assertNotEquals(week.id, planner.weeklyFor(day, recentlyDone = setOf(week.id)).id)
        }
    }

    @Test
    fun controllerAvoidsImpulseDoneInLastThreeWeeks() {
        val journal = Journal(InMemoryJournalStore(), persistent = false)
        val c = LichtblickeController(planner, journal)
        // Der Impuls, der heute käme, wurde vor 10 Tagen schon erledigt
        val target = monday
        val wouldShow = planner.dailyFor(target)
        journal.add(de.lichtblickraum.core.journal.EntryKind.DAILY, wouldShow.id, wouldShow.title, target.minusDays(10), null)
        val shown = c.state(target, c.choices.value, journal.entries.value).today.impulse
        assertNotEquals(wouldShow.id, shown.id)
        // Nach mehr als drei Wochen darf er wieder erscheinen
        val later = c.state(target, c.choices.value, listOf(journal.entries.value.single().copy(date = target.minusDays(30)))).today.impulse
        assertEquals(wouldShow.id, later.id)
    }
}

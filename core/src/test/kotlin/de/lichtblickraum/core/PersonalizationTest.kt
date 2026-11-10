package de.lichtblickraum.core

import de.lichtblickraum.core.exercises.ExerciseCategory
import de.lichtblickraum.core.impulses.DailyImpulses
import de.lichtblickraum.core.impulses.ImpulsePlanner
import de.lichtblickraum.core.impulses.ImpulseTheme
import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.journal.InMemoryJournalStore
import de.lichtblickraum.core.journal.Journal
import de.lichtblickraum.core.journal.JournalEntry
import de.lichtblickraum.core.mood.MoodContent
import de.lichtblickraum.core.mood.MoodInsights
import de.lichtblickraum.core.profile.Focus
import de.lichtblickraum.core.profile.Personalization
import de.lichtblickraum.core.profile.TimePreference
import de.lichtblickraum.core.profile.UserProfile
import de.lichtblickraum.core.thoughts.DailyThoughts
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class PersonalizationTest {
    private val day = LocalDate.of(2026, 11, 10)

    @Test
    fun fifteenThoughtsPerFocus() {
        Focus.entries.forEach { f -> assertEquals(f.title, 15, DailyThoughts.all.count { it.focus == f }) }
        assertEquals(DailyThoughts.all.size, DailyThoughts.all.map { it.id }.toSet().size)
        assertEquals(DailyThoughts.all.size, DailyThoughts.all.map { it.text }.toSet().size)
    }

    @Test
    fun thoughtsFollowChosenFocusAndChangeDaily() {
        val focus = setOf(Focus.ADHD)
        (0L..60L).forEach { assertEquals(Focus.ADHD, DailyThoughts.forDay(day.plusDays(it), focus).focus) }
        assertNotEquals(DailyThoughts.forDay(day, focus), DailyThoughts.forDay(day.plusDays(1), focus))
        // 15 Tage hintereinander: jeder Text einmal
        val n = 15L
        val start = LocalDate.ofEpochDay(Math.floorDiv(day.toEpochDay(), n) * n)
        assertEquals(15, (0L until n).map { DailyThoughts.forDay(start.plusDays(it), focus).id }.toSet().size)
        // Ohne Auswahl: alle Themen möglich
        assertTrue((0L..200L).map { DailyThoughts.forDay(day.plusDays(it), emptySet()).focus }.toSet().size == 5)
    }

    @Test
    fun preferredImpulsesComeMoreOftenButOthersToo() {
        val planner = ImpulsePlanner()
        val preferred = setOf(ImpulseTheme.BOUNDARIES)
        val picks = (0L until 300L).map { planner.dailyFor(day.plusDays(it), preferred = preferred) }
        val prefShare = picks.count { it.theme in preferred } / picks.size.toDouble()
        assertTrue("bevorzugt ca. 2/3, war $prefShare", prefShare in 0.6..0.72)
        assertTrue("andere Themen kommen auch vor", picks.any { it.theme !in preferred })
        // keine Wiederholung innerhalb von 5 Tagen
        picks.map { it.id }.windowed(5).forEach { assertEquals(it.size, it.toSet().size) }
        // alle bevorzugten werden erreicht
        assertEquals(DailyImpulses.all.count { it.theme in preferred }, picks.filter { it.theme in preferred }.map { it.id }.toSet().size)
    }

    @Test
    fun suggestedExercisesFollowFocusAndTime() {
        val p = UserProfile(onboardingDone = true, focus = setOf(Focus.ADHD, Focus.COUPLE), time = TimePreference.SHORT)
        val list = Personalization.suggestedExercises(p)
        assertTrue(list.all { it.category == ExerciseCategory.ADHD || it.category == ExerciseCategory.COUPLE })
        assertTrue("kurze zuerst", list.first().minutes <= 5)
        assertTrue(Personalization.exerciseForDay(p, day).minutes <= 5)
        assertEquals(de.lichtblickraum.core.exercises.ExerciseCatalog.all.size, Personalization.suggestedExercises(UserProfile()).size)
    }

    @Test
    fun reviewInviteRoughlyMonthly() {
        val p = UserProfile(onboardingDone = true, reviewInviteBase = day)
        assertFalse(Personalization.reviewInviteDue(p, day.plusDays(29)))
        assertTrue(Personalization.reviewInviteDue(p, day.plusDays(30)))
        assertFalse(Personalization.reviewInviteDue(UserProfile(), day.plusDays(100)))
    }

    private fun mood(date: LocalDate, level: Int) =
        JournalEntry("m$date$level", EntryKind.MOOD, "barometer", "Gefühlsbarometer", date, null, 0L, level)

    @Test
    fun supportHintAfterFiveHardDaysInAWeek() {
        val four = (0L..3L).map { mood(day.minusDays(it), 2) }
        assertFalse(MoodInsights.needsSupportHint(four, day))
        val five = four + mood(day.minusDays(5), 1)
        assertTrue(MoodInsights.needsSupportHint(five, day))
        // ein guter Eintrag am selben Tag hebt den Tagesschnitt
        assertFalse(MoodInsights.needsSupportHint(five + mood(day, 5), day))
        // länger als 7 Tage her zählt nicht
        assertFalse(MoodInsights.needsSupportHint(five.map { it.copy(date = it.date.minusDays(10)) }, day))
    }

    @Test
    fun moodEntriesAreStoredWithLevelAndTags() {
        val j = Journal(InMemoryJournalStore(), persistent = true)
        val e = j.add(EntryKind.MOOD, "barometer", "Gefühlsbarometer", day, " ", level = 3, tags = listOf("müde"))
        assertEquals(3, e.level)
        assertEquals(listOf("müde"), e.tags)
        assertNull(e.note)
    }

    @Test
    fun diaryTextJoinsAnsweredPrompts() {
        assertNull(MoodContent.diaryText(mapOf(0 to "  ")))
        assertEquals("Was ist passiert?\nStreit\n\nWas hätte ich gebraucht?\nRuhe",
            MoodContent.diaryText(mapOf(0 to "Streit", 3 to "Ruhe")))
    }
}

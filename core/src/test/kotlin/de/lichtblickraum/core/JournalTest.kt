package de.lichtblickraum.core

import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.journal.InMemoryJournalStore
import de.lichtblickraum.core.journal.Journal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class JournalTest {

    private val day = LocalDate.of(2026, 11, 3)

    @Test
    fun withoutConsentNothingIsStored() {
        val store = InMemoryJournalStore()
        val j = Journal(store, persistent = false)
        j.add(EntryKind.DAILY, "t01", "A", day, "Notiz")
        assertEquals(1, j.entries.value.size)
        assertTrue(store.load().isEmpty())
        // "Neustart" der App
        assertTrue(Journal(store, persistent = false).entries.value.isEmpty())
    }

    @Test
    fun consentStoresSessionEntriesAndSurvivesRestart() {
        val store = InMemoryJournalStore()
        val j = Journal(store, persistent = false)
        j.add(EntryKind.DAILY, "t01", "A", day, null)
        j.enablePersistence()
        j.add(EntryKind.EXERCISE, "orientierung", "B", day, null)
        assertEquals(2, store.load().size)
        assertEquals(2, Journal(store, persistent = true).entries.value.size)
    }

    @Test
    fun singleAndCompleteDeletion() {
        val store = InMemoryJournalStore()
        val j = Journal(store, persistent = true)
        val a = j.add(EntryKind.DAILY, "t01", "A", day, null)
        j.add(EntryKind.WEEKLY, "w01", "B", day, null)
        j.delete(a.id)
        assertEquals(listOf("B"), j.entries.value.map { it.title })
        assertEquals(listOf("B"), store.load().map { it.title })
        j.deleteAll()
        assertTrue(j.entries.value.isEmpty())
        assertTrue(store.load().isEmpty())
    }

    @Test
    fun withdrawingConsentDeletesStoredDataButKeepsSession() {
        val store = InMemoryJournalStore()
        val j = Journal(store, persistent = true)
        j.add(EntryKind.DAILY, "t01", "A", day, null)
        j.disablePersistence()
        assertTrue(store.load().isEmpty())
        assertEquals(1, j.entries.value.size)
        j.add(EntryKind.DAILY, "t02", "B", day, null)
        assertTrue(store.load().isEmpty())
    }

    @Test
    fun newestFirst() {
        var t = 0L
        val j = Journal(InMemoryJournalStore(), persistent = false, clock = { t++ })
        j.add(EntryKind.DAILY, "x", "alt", day.minusDays(2), null)
        j.add(EntryKind.DAILY, "y", "neu", day, null)
        j.add(EntryKind.DAILY, "z", "neu2", day, null)
        assertEquals(listOf("neu2", "neu", "alt"), j.entries.value.map { it.title })
    }
}

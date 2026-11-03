package de.lichtblickraum.core.journal

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.UUID

enum class EntryKind(val label: String) {
    DAILY("Lichtblick für heute"),
    WEEKLY("Wochenimpuls"),
    EXERCISE("Übung"),
}

data class JournalEntry(
    val id: String,
    val kind: EntryKind,
    val itemId: String,
    val title: String,
    val date: LocalDate,
    /** Freiwillige Notiz; null = ohne schriftliche Antwort abgeschlossen. */
    val note: String?,
    val createdAtMillis: Long,
)

/** Dauerhafter Speicher auf dem Gerät (Umsetzung in der App, z. B. als Datei). */
interface JournalStore {
    fun load(): List<JournalEntry>
    fun saveAll(entries: List<JournalEntry>)
    fun deleteAll()
}

/**
 * Rückschau-Einträge (erledigte Impulse und Übungen).
 *
 * - Ohne Zustimmung ([isPersistent] = false) liegen Einträge nur im Arbeitsspeicher
 *   und verschwinden, wenn die App beendet wird.
 * - Mit Zustimmung wird jede Änderung sofort in [store] geschrieben.
 * - Einträge sind einzeln und vollständig löschbar.
 * - Es gibt keinerlei Übertragung an Dritte – diese Klasse kennt nur den lokalen [store].
 */
class Journal(
    private val store: JournalStore,
    persistent: Boolean,
    private val clock: () -> Long = System::currentTimeMillis,
) {
    private val _entries = MutableStateFlow<List<JournalEntry>>(emptyList())
    val entries: StateFlow<List<JournalEntry>> = _entries.asStateFlow()

    private val _isPersistent = MutableStateFlow(persistent)
    val isPersistent: StateFlow<Boolean> = _isPersistent.asStateFlow()

    init {
        if (persistent) _entries.value = sorted(store.load())
    }

    fun add(kind: EntryKind, itemId: String, title: String, date: LocalDate, note: String?): JournalEntry {
        val entry = JournalEntry(
            id = UUID.randomUUID().toString(),
            kind = kind,
            itemId = itemId,
            title = title,
            date = date,
            note = note?.trim()?.takeIf { it.isNotEmpty() },
            createdAtMillis = clock(),
        )
        update(_entries.value + entry)
        return entry
    }

    fun delete(id: String) = update(_entries.value.filterNot { it.id == id })

    /** Löscht alle Einträge – im Arbeitsspeicher und auf dem Gerät. */
    fun deleteAll() {
        _entries.value = emptyList()
        store.deleteAll()
    }

    /** Zustimmung erteilt: bisherige Einträge dieser Sitzung werden ab jetzt gespeichert. */
    fun enablePersistence() {
        if (_isPersistent.value) return
        _isPersistent.value = true
        val merged = (store.load() + _entries.value).distinctBy { it.id }
        update(merged)
    }

    /**
     * Zustimmung zurückgezogen: Die Datei auf dem Gerät wird gelöscht.
     * Einträge dieser Sitzung bleiben sichtbar, bis die App beendet wird.
     */
    fun disablePersistence() {
        _isPersistent.value = false
        store.deleteAll()
    }

    private fun update(list: List<JournalEntry>) {
        val s = sorted(list)
        _entries.value = s
        if (_isPersistent.value) store.saveAll(s)
    }

    private fun sorted(list: List<JournalEntry>) =
        list.sortedWith(compareByDescending<JournalEntry> { it.date }.thenByDescending { it.createdAtMillis })
}

/** Speicher, der nichts dauerhaft ablegt (z. B. für Tests). */
class InMemoryJournalStore : JournalStore {
    private var data: List<JournalEntry> = emptyList()
    override fun load() = data
    override fun saveAll(entries: List<JournalEntry>) { data = entries }
    override fun deleteAll() { data = emptyList() }
}

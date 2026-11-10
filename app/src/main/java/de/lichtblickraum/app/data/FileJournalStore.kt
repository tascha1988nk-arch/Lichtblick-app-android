package de.lichtblickraum.app.data

import android.util.AtomicFile
import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.journal.JournalEntry
import de.lichtblickraum.core.journal.JournalStore
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.time.LocalDate

/**
 * Speichert Rückschau-Einträge als Datei im privaten App-Speicher.
 * Andere Apps haben keinen Zugriff; die Datei ist von Sicherungen ausgeschlossen
 * und wird beim Deinstallieren gelöscht. Wird nur genutzt, wenn Speichern erlaubt wurde.
 */
class FileJournalStore(private val file: File) : JournalStore {

    private val atomic = AtomicFile(file)

    override fun load(): List<JournalEntry> = try {
        if (!file.exists()) emptyList() else {
            val arr = JSONArray(String(atomic.readFully(), Charsets.UTF_8))
            (0 until arr.length()).mapNotNull { i -> arr.optJSONObject(i)?.toEntry() }
        }
    } catch (e: Exception) {
        emptyList()
    }

    override fun saveAll(entries: List<JournalEntry>) {
        val arr = JSONArray()
        entries.forEach { arr.put(it.toJson()) }
        val out = atomic.startWrite()
        try {
            out.write(arr.toString().toByteArray(Charsets.UTF_8))
            atomic.finishWrite(out)
        } catch (e: Exception) {
            atomic.failWrite(out)
        }
    }

    override fun deleteAll() {
        atomic.delete()
    }

    private fun JournalEntry.toJson() = JSONObject().apply {
        put("id", id)
        put("kind", kind.name)
        put("itemId", itemId)
        put("title", title)
        put("date", date.toString())
        if (note != null) put("note", note)
        put("createdAt", createdAtMillis)
        if (level != null) put("level", level)
        if (tags.isNotEmpty()) put("tags", JSONArray(tags))
    }

    private fun JSONObject.toEntry(): JournalEntry? = try {
        JournalEntry(
            id = getString("id"),
            kind = EntryKind.valueOf(getString("kind")),
            itemId = getString("itemId"),
            title = getString("title"),
            date = LocalDate.parse(getString("date")),
            note = if (has("note")) getString("note") else null,
            createdAtMillis = getLong("createdAt"),
            level = if (has("level")) getInt("level") else null,
            tags = optJSONArray("tags")?.let { a -> (0 until a.length()).map { a.getString(it) } } ?: emptyList(),
        )
    } catch (e: Exception) {
        null
    }
}

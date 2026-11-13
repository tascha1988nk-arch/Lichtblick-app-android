package de.lichtblickraum.core.selftest

import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.journal.JournalEntry

/** Ein Bereich im Vergleich zweier Ergebnisse. */
data class ScoreChange(val name: String, val before: Int, val after: Int) {
    val difference: Int get() = after - before
}

/**
 * Speichern, Wiederherstellen und Vergleichen von Selbsttest-Ergebnissen.
 * Gespeichert werden nur die Werte je Bereich – nie die einzelnen Antworten.
 */
object SelfTestResults {

    /** Name für den Gesamtwert bei Tests ohne Bereiche. */
    const val OVERALL = "Gesamt"

    fun toScores(q: Questionnaire, result: QuestionnaireResult): Map<String, Int> {
        val map = LinkedHashMap<String, Int>()
        if (q.bands.isNotEmpty() || result.dimensions.isEmpty()) map[OVERALL] = result.percent
        result.dimensions.forEach { map[it.dimension] = it.percent }
        return map
    }

    /** Gesamtbereich (falls vorhanden) und Bereiche – aus gespeicherten Werten neu aufgebaut. */
    fun overallBand(q: Questionnaire, scores: Map<String, Int>): ResultBand? =
        scores[OVERALL]?.let { p -> q.bands.firstOrNull { p in it.fromPercent..it.toPercent } }

    fun dimensionScores(q: Questionnaire, scores: Map<String, Int>): List<DimensionScore> =
        q.dimensions.mapNotNull { d ->
            scores[d.name]?.let { p -> DimensionScore(d.name, p, d.bands.firstOrNull { p in it.fromPercent..it.toPercent }) }
        }

    /** Alle gespeicherten Ergebnisse eines Tests, älteste zuerst. */
    fun history(entries: List<JournalEntry>, questionnaireId: String): List<JournalEntry> =
        entries.filter { it.kind == EntryKind.SELF_TEST && it.itemId == questionnaireId }
            .sortedWith(compareBy<JournalEntry> { it.date }.thenBy { it.createdAtMillis })

    /** Das Ergebnis direkt vor [entry] (oder null beim ersten Mal). */
    fun previous(entries: List<JournalEntry>, entry: JournalEntry): JournalEntry? {
        val h = history(entries, entry.itemId)
        val i = h.indexOfFirst { it.id == entry.id }
        return if (i > 0) h[i - 1] else null
    }

    fun compare(before: Map<String, Int>, after: Map<String, Int>): List<ScoreChange> =
        after.keys.filter { it in before }.map { ScoreChange(it, before.getValue(it), after.getValue(it)) }

    /** Wertfreie Beschreibung einer Veränderung – ohne „besser“ oder „schlechter“. */
    fun describe(change: ScoreChange): String = when {
        change.difference == 0 -> "unverändert"
        kotlin.math.abs(change.difference) < 5 -> "fast unverändert"
        change.difference > 0 -> "etwas stärker ausgeprägt (+${change.difference})"
        else -> "etwas weniger ausgeprägt (${change.difference})"
    }
}

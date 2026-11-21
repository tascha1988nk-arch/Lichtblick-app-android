package de.lichtblickraum.core.mood

import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.journal.JournalEntry
import java.time.LocalDate

/** Stufen des Gefühlsbarometers – mit Worten und Wetterbildern statt nüchterner Zahlen. */
enum class MoodLevel(val value: Int, val label: String, val symbol: String) {
    VERY_HARD(1, "Sehr schwer", "⛈️"),
    HARD(2, "Eher schwer", "🌧️"),
    MIXED(3, "Gemischt", "⛅"),
    GOOD(4, "Eher gut", "🌤️"),
    VERY_GOOD(5, "Gut", "☀️"),
    ;

    companion object {
        fun of(value: Int?): MoodLevel? = entries.firstOrNull { it.value == value }
    }
}

object MoodContent {
    val feelingWords: List<String> = listOf(
        "ruhig", "zufrieden", "froh", "dankbar", "hoffnungsvoll",
        "müde", "erschöpft", "angespannt", "unruhig", "überfordert",
        "traurig", "einsam", "ängstlich", "ärgerlich", "enttäuscht",
    )

    /** Leitfragen des Gefühlstagebuchs – alle freiwillig. */
    val diaryPrompts: List<String> = listOf(
        "Was ist passiert?",
        "Was habe ich gefühlt?",
        "Wo habe ich es im Körper gespürt?",
        "Was hätte ich gebraucht?",
        "Was hat mir geholfen – oder könnte mir helfen?",
    )

    /** Fügt die beantworteten Leitfragen zu einem Text zusammen. */
    fun diaryText(answers: Map<Int, String>): String? = diaryPrompts.indices
        .mapNotNull { i -> answers[i]?.trim()?.takeIf { it.isNotEmpty() }?.let { "${diaryPrompts[i]}\n$it" } }
        .joinToString("\n\n")
        .takeIf { it.isNotEmpty() }

    const val SUPPORT_TITLE = "Es scheint gerade schwer zu sein"
    const val SUPPORT_TEXT =
        "In den letzten Tagen ging es dir oft nicht gut. Das ist ernst zu nehmen – und du musst " +
            "das nicht allein tragen. Die TelefonSeelsorge ist rund um die Uhr für dich da, " +
            "kostenfrei und anonym."
    const val SUPPORT_PHONE = "116 123"
    const val SUPPORT_PHONE_DIAL = "116123"
}

object MoodInsights {

    /** Ab so vielen schweren Tagen innerhalb der letzten [WINDOW_DAYS] Tage erscheint der Hilfe-Hinweis. */
    const val HARD_DAYS_FOR_HINT = 5
    const val WINDOW_DAYS = 7L

    /** Durchschnittliche Stufe je Tag (nur Tage mit Eintrag – Lücken gibt es nicht). */
    fun dailyAverages(entries: List<JournalEntry>): Map<LocalDate, Double> = entries
        .filter { it.kind == EntryKind.MOOD && it.level != null }
        .groupBy { it.date }
        .mapValues { (_, list) -> list.mapNotNull { it.level }.average() }
        .toSortedMap()

    /** true, wenn es an mindestens 5 der letzten 7 Tage eher schwer oder sehr schwer war. */
    fun needsSupportHint(entries: List<JournalEntry>, today: LocalDate): Boolean {
        val from = today.minusDays(WINDOW_DAYS - 1)
        val hardDays = dailyAverages(entries).count { (day, avg) -> !day.isBefore(from) && !day.isAfter(today) && avg <= 2.0 }
        return hardDays >= HARD_DAYS_FOR_HINT
    }
}

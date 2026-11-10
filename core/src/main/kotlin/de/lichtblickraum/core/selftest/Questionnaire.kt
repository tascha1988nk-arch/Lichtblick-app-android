package de.lichtblickraum.core.selftest

/** Eine Antwortmöglichkeit, z. B. "Trifft eher zu" mit dem Wert 3. */
data class AnswerOption(val label: String, val value: Int)

/**
 * Eine Aussage im Fragebogen.
 *
 * @param reversed true, wenn eine hohe Zustimmung in der Auswertung niedrig zählen soll
 *                 (umgepolte Aussage).
 * @param dimension optionaler Unterbereich, z. B. "Grenzen" – für spätere Tests mit Teilbereichen.
 */
data class Question(
    val id: String,
    val text: String,
    val reversed: Boolean = false,
    val dimension: String? = null,
)

/**
 * Ein Auswertungsbereich. Prozentwerte beziehen sich auf die Spanne vom kleinstmöglichen (0 %)
 * bis zum größtmöglichen Gesamtwert (100 %). Grenzen sind inklusive.
 */
data class ResultBand(
    val fromPercent: Int,
    val toPercent: Int,
    val title: String,
    val text: String,
    /** Optional: ein erlaubender Satz („Dein Erlauber“). */
    val permission: String? = null,
    /** Optional: eine Frage zum Weiterdenken. */
    val reflection: String? = null,
)

/**
 * Ein Anteil/Bereich eines Tests (z. B. „Perfektionist & Antreiber“) mit eigener Auswertung.
 * Fragen gehören über [Question.dimension] = [name] dazu.
 */
data class Dimension(
    val name: String,
    val bands: List<ResultBand>,
)

enum class QuestionnaireStatus { AVAILABLE, IN_PREPARATION }

data class Questionnaire(
    val id: String,
    val title: String,
    val shortDescription: String,
    val intro: String,
    val status: QuestionnaireStatus,
    /** true = Beispiel ohne fachliche Grundlage; wird in der App deutlich gekennzeichnet. */
    val isExample: Boolean,
    val estimatedMinutes: Int,
    val options: List<AnswerOption> = emptyList(),
    val questions: List<Question> = emptyList(),
    /** Gesamtauswertung (optional, wenn der Test nur Anteile auswertet). */
    val bands: List<ResultBand> = emptyList(),
    /** Anteile mit eigener Auswertung (optional). */
    val dimensions: List<Dimension> = emptyList(),
    /** Optionaler Abschluss unter dem Ergebnis, z. B. „Möchtest du genauer hinschauen?“ */
    val closingTitle: String? = null,
    /** Satz vor dem ausgeprägtesten Bereich, z. B. „Am deutlichsten zeigt sich gerade:“ (null = nicht anzeigen). */
    val strongestHint: String? = null,
    val closingText: String? = null,
    /** Wird unter jedem Ergebnis gezeigt. */
    val resultNote: String = "",
) {
    val isAvailable: Boolean get() = status == QuestionnaireStatus.AVAILABLE && questions.isNotEmpty()
}

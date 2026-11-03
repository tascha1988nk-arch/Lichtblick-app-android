package de.lichtblickraum.core.selftest

import kotlin.math.roundToInt

data class DimensionScore(val dimension: String, val percent: Int)

data class QuestionnaireResult(
    val score: Int,
    val minScore: Int,
    val maxScore: Int,
    /** 0–100, Lage des Gesamtwerts zwischen kleinst- und größtmöglichem Wert. */
    val percent: Int,
    val band: ResultBand,
    val dimensions: List<DimensionScore>,
)

/**
 * Rechnet Antworten in ein Ergebnis um. Die Regeln (Werte, Umpolung, Bereiche)
 * kommen vollständig aus dem jeweiligen [Questionnaire] – hier steht keine Fachlogik.
 */
object QuestionnaireEvaluator {

    fun evaluate(questionnaire: Questionnaire, answers: Map<String, Int>): QuestionnaireResult {
        require(questionnaire.isAvailable) { "Fragebogen '${questionnaire.id}' ist nicht verfügbar." }
        val missing = questionnaire.questions.filter { it.id !in answers }
        require(missing.isEmpty()) { "Nicht alle Fragen beantwortet: ${missing.map { it.id }}" }

        val minValue = questionnaire.options.minOf { it.value }
        val maxValue = questionnaire.options.maxOf { it.value }
        val allowed = questionnaire.options.map { it.value }.toSet()

        fun scored(q: Question): Int {
            val raw = answers.getValue(q.id)
            require(raw in allowed) { "Ungültiger Antwortwert $raw für ${q.id}" }
            return if (q.reversed) maxValue + minValue - raw else raw
        }

        val score = questionnaire.questions.sumOf { scored(it) }
        val minScore = minValue * questionnaire.questions.size
        val maxScore = maxValue * questionnaire.questions.size
        val percent = toPercent(score, minScore, maxScore)

        val band = questionnaire.bands.firstOrNull { percent in it.fromPercent..it.toPercent }
            ?: error("Kein Auswertungsbereich für $percent % in '${questionnaire.id}'.")

        val dimensions = questionnaire.questions
            .filter { it.dimension != null }
            .groupBy { it.dimension!! }
            .map { (name, qs) ->
                DimensionScore(
                    dimension = name,
                    percent = toPercent(qs.sumOf { scored(it) }, minValue * qs.size, maxValue * qs.size),
                )
            }

        return QuestionnaireResult(score, minScore, maxScore, percent, band, dimensions)
    }

    private fun toPercent(value: Int, min: Int, max: Int): Int =
        if (max == min) 0 else ((value - min) * 100.0 / (max - min)).roundToInt()

    /** Liefert eine Liste von Problemen; leer = Fragebogen ist in sich stimmig. */
    fun validate(questionnaire: Questionnaire): List<String> {
        if (questionnaire.status == QuestionnaireStatus.IN_PREPARATION) return emptyList()
        val problems = mutableListOf<String>()
        if (questionnaire.questions.isEmpty()) problems += "keine Fragen"
        if (questionnaire.options.size < 2) problems += "weniger als zwei Antwortmöglichkeiten"
        if (questionnaire.questions.map { it.id }.toSet().size != questionnaire.questions.size) {
            problems += "doppelte Fragen-IDs"
        }
        for (p in 0..100) {
            val hits = questionnaire.bands.count { p in it.fromPercent..it.toPercent }
            if (hits == 0) problems += "kein Auswertungsbereich für $p %"
            if (hits > 1) problems += "mehrere Auswertungsbereiche für $p %"
        }
        return problems.distinct()
    }
}

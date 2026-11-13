package de.lichtblickraum.core.selftest

import kotlin.math.roundToInt

data class DimensionScore(val dimension: String, val percent: Int, val band: ResultBand? = null)

data class QuestionnaireResult(
    val score: Int,
    val minScore: Int,
    val maxScore: Int,
    /** 0–100, Lage des Gesamtwerts zwischen kleinst- und größtmöglichem Wert. */
    val percent: Int,
    /** Gesamtbereich; null, wenn der Test nur Anteile auswertet. */
    val band: ResultBand?,
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

        val band = if (questionnaire.bands.isEmpty()) null else questionnaire.bands.firstOrNull { percent in it.fromPercent..it.toPercent }
            ?: error("Kein Auswertungsbereich für $percent % in '${questionnaire.id}'.")

        val dimensions = questionnaire.questions
            .filter { it.dimension != null }
            .groupBy { it.dimension!! }
            .map { (name, qs) ->
                val p = toPercent(qs.sumOf { scored(it) }, minValue * qs.size, maxValue * qs.size)
                val def = questionnaire.dimensions.firstOrNull { it.name == name }
                DimensionScore(
                    dimension = name,
                    percent = p,
                    band = def?.bands?.firstOrNull { p in it.fromPercent..it.toPercent },
                )
            }
            .sortedBy { d -> questionnaire.dimensions.indexOfFirst { it.name == d.dimension }.let { if (it < 0) Int.MAX_VALUE else it } }

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
        if (questionnaire.bands.isEmpty() && questionnaire.dimensions.isEmpty()) problems += "keine Auswertung"
        fun checkBands(label: String, bands: List<ResultBand>) {
            if (bands.isEmpty()) return
            for (p in 0..100) {
                val hits = bands.count { p in it.fromPercent..it.toPercent }
                if (hits == 0) problems += "${label}kein Auswertungsbereich für $p %"
                if (hits > 1) problems += "${label}mehrere Auswertungsbereiche für $p %"
            }
        }
        checkBands("", questionnaire.bands)
        questionnaire.dimensions.forEach { d ->
            if (d.bands.isEmpty()) problems += "${d.name}: keine Auswertungsbereiche"
            checkBands("${d.name}: ", d.bands)
            if (questionnaire.questions.none { it.dimension == d.name }) problems += "${d.name}: keine Fragen"
        }
        val names = questionnaire.dimensions.map { it.name }.toSet()
        questionnaire.questions.filter { it.dimension != null && it.dimension !in names && questionnaire.dimensions.isNotEmpty() }
            .forEach { problems += "Frage ${it.id}: unbekannter Bereich ${it.dimension}" }
        return problems.distinct()
    }
}

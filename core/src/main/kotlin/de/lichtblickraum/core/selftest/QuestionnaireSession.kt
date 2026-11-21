package de.lichtblickraum.core.selftest

/**
 * Ablauf eines Fragebogens: aktuelle Frage, Antworten, Vor/Zurück, Fortschritt.
 *
 * Die Antworten liegen nur im Arbeitsspeicher dieses Objekts. Es gibt bewusst
 * keine Speicherfunktion – wird der Test verlassen, ist alles verworfen.
 */
class QuestionnaireSession(val questionnaire: Questionnaire) {

    init {
        require(questionnaire.isAvailable) { "Fragebogen '${questionnaire.id}' ist nicht verfügbar." }
    }

    private val answers = mutableMapOf<String, Int>()

    var currentIndex: Int = 0
        private set

    val questionCount: Int get() = questionnaire.questions.size
    val currentQuestion: Question get() = questionnaire.questions[currentIndex]
    val selectedValue: Int? get() = answers[currentQuestion.id]
    val isFirst: Boolean get() = currentIndex == 0
    val isLast: Boolean get() = currentIndex == questionCount - 1
    val canGoNext: Boolean get() = selectedValue != null
    val answeredCount: Int get() = answers.size
    val isComplete: Boolean get() = questionnaire.questions.all { it.id in answers }

    /** Fortschritt von 0.0 bis 1.0 – zählt die aktuelle Frage als "erreicht". */
    val progress: Float get() = (currentIndex + 1).toFloat() / questionCount

    fun select(value: Int) {
        require(questionnaire.options.any { it.value == value }) { "Ungültiger Wert $value" }
        answers[currentQuestion.id] = value
    }

    /** @return true, wenn weitergeblättert wurde. */
    fun next(): Boolean {
        if (!canGoNext || isLast) return false
        currentIndex++
        return true
    }

    /** @return true, wenn zurückgeblättert wurde. */
    fun previous(): Boolean {
        if (isFirst) return false
        currentIndex--
        return true
    }

    fun result(): QuestionnaireResult = QuestionnaireEvaluator.evaluate(questionnaire, answers.toMap())

    /** Verwirft alle Antworten. */
    fun discard() {
        answers.clear()
        currentIndex = 0
    }
}

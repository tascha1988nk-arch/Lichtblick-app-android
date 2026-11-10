package de.lichtblickraum.app.ui.selftest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.lichtblickraum.core.selftest.Questionnaire
import de.lichtblickraum.core.selftest.QuestionnaireResult
import de.lichtblickraum.core.selftest.QuestionnaireSession

enum class SelfTestPhase { INTRO, QUESTIONS, RESULT }

/**
 * Hält den Testablauf nur im Arbeitsspeicher. Wird der Test verlassen,
 * räumt Android dieses Objekt weg und [onCleared] verwirft alle Antworten.
 */
class SelfTestViewModel(
    questionnaire: Questionnaire,
    /** Speichert das Ergebnis (nur die Werte je Bereich) und gibt die Eintrags-ID zurück. */
    private val onResult: (Questionnaire, QuestionnaireResult) -> String?,
) : ViewModel() {
    private val session = QuestionnaireSession(questionnaire)

    var phase by mutableStateOf(SelfTestPhase.INTRO)
        private set
    var result by mutableStateOf<QuestionnaireResult?>(null)
        private set
    var savedEntryId by mutableStateOf<String?>(null)
        private set

    // Ändert sich bei jeder Aktion, damit die Oberfläche neu zeichnet.
    private var version by mutableIntStateOf(0)

    val questionnaire get() = session.questionnaire
    val index: Int get() { version; return session.currentIndex }
    val count: Int get() = session.questionCount
    val progress: Float get() { version; return session.progress }
    val question get() = run { version; session.currentQuestion }
    val selected: Int? get() { version; return session.selectedValue }
    val canGoNext: Boolean get() { version; return session.canGoNext }
    val isLast: Boolean get() { version; return session.isLast }
    val hasAnswers: Boolean get() { version; return session.answeredCount > 0 }

    fun start() { phase = SelfTestPhase.QUESTIONS }

    fun select(value: Int) { session.select(value); version++ }

    fun next() {
        if (session.isLast && session.isComplete) {
            val r = session.result()
            result = r
            savedEntryId = onResult(session.questionnaire, r)
            session.discard() // einzelne Antworten sofort verwerfen
            phase = SelfTestPhase.RESULT
        } else {
            session.next()
        }
        version++
    }

    fun previous() {
        if (!session.previous()) phase = SelfTestPhase.INTRO
        version++
    }

    fun restart() {
        session.discard()
        result = null
        savedEntryId = null
        phase = SelfTestPhase.INTRO
        version++
    }

    override fun onCleared() {
        session.discard()
        result = null
    }
}

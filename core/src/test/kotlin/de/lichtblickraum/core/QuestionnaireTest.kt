package de.lichtblickraum.core

import de.lichtblickraum.core.selftest.AnswerOption
import de.lichtblickraum.core.selftest.Question
import de.lichtblickraum.core.selftest.Questionnaire
import de.lichtblickraum.core.selftest.QuestionnaireEvaluator
import de.lichtblickraum.core.selftest.QuestionnaireSession
import de.lichtblickraum.core.selftest.QuestionnaireStatus
import de.lichtblickraum.core.selftest.ResultBand
import de.lichtblickraum.core.selftest.SelfTestCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestionnaireTest {

    private val example = SelfTestCatalog.example

    private fun answersAll(value: Int) = example.questions.associate { it.id to value }

    @Test
    fun catalogIsConsistent() {
        (SelfTestCatalog.all + example).forEach { q ->
            assertEquals("Probleme in ${q.id}", emptyList<String>(), QuestionnaireEvaluator.validate(q))
        }
        assertEquals(SelfTestCatalog.all.size, SelfTestCatalog.all.map { it.id }.toSet().size)
        assertTrue(example.isExample)
        assertTrue(example.resultNote.contains("keine Diagnose"))
    }

    @Test
    fun innerCriticIsAvailableAndExampleHidden() {
        assertEquals(listOf(SelfTestCatalog.innerCritic.id), SelfTestCatalog.all.filter { it.isAvailable }.map { it.id })
        assertTrue(SelfTestCatalog.byId(example.id) == null)
    }

    @Test
    fun reversedQuestionsAreInverted() {
        // Alle "Trifft voll zu" (4): 6 normale * 4 + 2 umgepolte * 0 = 24 von 32
        val r = QuestionnaireEvaluator.evaluate(example, answersAll(4))
        assertEquals(24, r.score)
        assertEquals(0, r.minScore)
        assertEquals(32, r.maxScore)
        assertEquals(75, r.percent)
        assertEquals("Oft gehst du schon freundlich mit dir um", r.band?.title)
    }

    @Test
    fun extremesMapToZeroAndHundred() {
        val low = example.questions.associate { it.id to if (it.reversed) 4 else 0 }
        val high = example.questions.associate { it.id to if (it.reversed) 0 else 4 }
        assertEquals(0, QuestionnaireEvaluator.evaluate(example, low).percent)
        assertEquals(100, QuestionnaireEvaluator.evaluate(example, high).percent)
        assertEquals("Gerade bist du eher streng mit dir", QuestionnaireEvaluator.evaluate(example, low).band?.title)
    }

    @Test
    fun middleAnswersGiveMiddleBand() {
        val r = QuestionnaireEvaluator.evaluate(example, answersAll(2))
        assertEquals(50, r.percent)
        assertEquals("Mal so, mal so", r.band?.title)
    }

    @Test
    fun bandBoundariesAreInclusive() {
        val q = Questionnaire(
            id = "t", title = "t", shortDescription = "", intro = "", status = QuestionnaireStatus.AVAILABLE,
            isExample = true, estimatedMinutes = 1,
            options = (0..10).map { AnswerOption("$it", it) },
            questions = listOf(Question("a", "a")),
            bands = listOf(ResultBand(0, 39, "low", ""), ResultBand(40, 69, "mid", ""), ResultBand(70, 100, "high", "")),
        )
        assertEquals("low", QuestionnaireEvaluator.evaluate(q, mapOf("a" to 3)).band?.title) // 30 %
        assertEquals("mid", QuestionnaireEvaluator.evaluate(q, mapOf("a" to 4)).band?.title) // 40 %
        assertEquals("mid", QuestionnaireEvaluator.evaluate(q, mapOf("a" to 6)).band?.title) // 60 %
        assertEquals("high", QuestionnaireEvaluator.evaluate(q, mapOf("a" to 7)).band?.title) // 70 %
    }

    @Test
    fun dimensionsAreScoredSeparately() {
        val q = Questionnaire(
            id = "d", title = "d", shortDescription = "", intro = "", status = QuestionnaireStatus.AVAILABLE,
            isExample = true, estimatedMinutes = 1, options = SelfTestCatalog.agreementScale,
            questions = listOf(Question("a", "a", dimension = "X"), Question("b", "b", dimension = "Y", reversed = true)),
            bands = listOf(ResultBand(0, 100, "all", "")),
        )
        val r = QuestionnaireEvaluator.evaluate(q, mapOf("a" to 4, "b" to 4))
        assertEquals(mapOf("X" to 100, "Y" to 0), r.dimensions.associate { it.dimension to it.percent })
    }

    @Test(expected = IllegalArgumentException::class)
    fun incompleteAnswersAreRejected() {
        QuestionnaireEvaluator.evaluate(example, mapOf("q1" to 2))
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidValuesAreRejected() {
        QuestionnaireEvaluator.evaluate(example, answersAll(2) + ("q1" to 9))
    }

    @Test
    fun validationFindsGapsAndOverlaps() {
        val broken = example.copy(bands = listOf(ResultBand(0, 50, "a", ""), ResultBand(50, 90, "b", "")))
        val problems = QuestionnaireEvaluator.validate(broken)
        assertTrue(problems.any { it.contains("mehrere") })
        assertTrue(problems.any { it.contains("kein Auswertungsbereich für 95") })
    }

    @Test
    fun sessionFlowWithProgressBackAndDiscard() {
        val s = QuestionnaireSession(example)
        assertEquals(8, s.questionCount)
        assertTrue(s.isFirst)
        assertFalse(s.canGoNext)
        assertFalse("ohne Antwort nicht weiter", s.next())
        assertEquals(1f / 8, s.progress, 0.0001f)

        s.select(3)
        assertTrue(s.next())
        assertEquals(1, s.currentIndex)
        assertNull(s.selectedValue)
        assertTrue(s.previous())
        assertEquals("Antwort bleibt beim Zurückgehen erhalten", 3, s.selectedValue)
        assertFalse(s.previous())

        // Rest beantworten
        s.next()
        while (true) {
            s.select(2)
            if (s.isLast) break
            assertTrue(s.next())
        }
        assertEquals(1f, s.progress, 0.0001f)
        assertFalse("auf der letzten Frage gibt es kein Weiter", s.next())
        assertTrue(s.isComplete)
        val r = s.result()
        // q1=3, Rest 2 -> 17 von 32 -> 53 %
        assertEquals(17, r.score)
        assertEquals(53, r.percent)

        s.discard()
        assertEquals(0, s.answeredCount)
        assertEquals(0, s.currentIndex)
        assertFalse(s.isComplete)
    }

    @Test(expected = IllegalArgumentException::class)
    fun inPreparationCannotBeStarted() {
        QuestionnaireSession(SelfTestCatalog.peoplePleasing)
    }
}

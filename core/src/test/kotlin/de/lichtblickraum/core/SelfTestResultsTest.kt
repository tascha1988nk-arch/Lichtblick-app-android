package de.lichtblickraum.core

import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.journal.InMemoryJournalStore
import de.lichtblickraum.core.journal.Journal
import de.lichtblickraum.core.selftest.AnswerOption
import de.lichtblickraum.core.selftest.Dimension
import de.lichtblickraum.core.selftest.Question
import de.lichtblickraum.core.selftest.Questionnaire
import de.lichtblickraum.core.selftest.QuestionnaireEvaluator
import de.lichtblickraum.core.selftest.QuestionnaireStatus
import de.lichtblickraum.core.selftest.ResultBand
import de.lichtblickraum.core.selftest.SelfTestCatalog
import de.lichtblickraum.core.selftest.SelfTestResults
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class SelfTestResultsTest {

    private fun bands(prefix: String) = listOf(
        ResultBand(0, 33, "$prefix niedrig", "t", permission = "e", reflection = "r"),
        ResultBand(34, 66, "$prefix mittel", "t"),
        ResultBand(67, 100, "$prefix hoch", "t"),
    )

    private val twoParts = Questionnaire(
        id = "zwei", title = "Zwei Bereiche", shortDescription = "", intro = "",
        status = QuestionnaireStatus.AVAILABLE, isExample = false, estimatedMinutes = 1,
        options = (0..4).map { AnswerOption("$it", it) },
        questions = listOf(
            Question("a1", "a1", dimension = "A"), Question("a2", "a2", dimension = "A"),
            Question("b1", "b1", dimension = "B"), Question("b2", "b2", dimension = "B", reversed = true),
        ),
        dimensions = listOf(Dimension("A", bands("A")), Dimension("B", bands("B"))),
    )

    @Test
    fun dimensionOnlyTestIsValidAndScoredPerPart() {
        assertEquals(emptyList<String>(), QuestionnaireEvaluator.validate(twoParts))
        val r = QuestionnaireEvaluator.evaluate(twoParts, mapOf("a1" to 4, "a2" to 4, "b1" to 0, "b2" to 4))
        assertNull(r.band)
        assertEquals(listOf("A" to 100, "B" to 0), r.dimensions.map { it.dimension to it.percent })
        assertEquals("A hoch", r.dimensions[0].band?.title)
        assertEquals("B niedrig", r.dimensions[1].band?.title)
        assertEquals("e", r.dimensions[1].band?.permission)
    }

    @Test
    fun validationFindsMissingPartsAndUnknownDimensions() {
        val broken = twoParts.copy(questions = twoParts.questions + Question("x", "x", dimension = "C"))
        assertTrue(QuestionnaireEvaluator.validate(broken).any { it.contains("unbekannter Bereich C") })
        val gap = twoParts.copy(dimensions = listOf(Dimension("A", bands("A").dropLast(1)), twoParts.dimensions[1]))
        assertTrue(QuestionnaireEvaluator.validate(gap).any { it.startsWith("A: kein Auswertungsbereich") })
    }

    @Test
    fun innerCriticIsCompleteAndBalanced() {
        val q = SelfTestCatalog.innerCritic
        assertEquals(emptyList<String>(), QuestionnaireEvaluator.validate(q))
        assertEquals(QuestionnaireStatus.AVAILABLE, q.status)
        assertEquals(listOf(1, 2, 3, 4, 5), q.options.map { it.value })
        assertEquals(24, q.questions.size)
        q.dimensions.forEach { d ->
            assertEquals(d.name, 6, q.questions.count { it.dimension == d.name })
            assertEquals(3, d.bands.size)
            d.bands.forEach { b ->
                assertTrue(b.permission != null && b.reflection != null)
                assertTrue("kein Platzhalter", !b.text.contains("[Platzhalter"))
            }
        }
        assertEquals(q.questions.size, q.questions.map { it.text }.toSet().size)
    }

    @Test
    fun innerCriticScoring() {
        val q = SelfTestCatalog.innerCritic
        fun all(v: Int) = QuestionnaireEvaluator.evaluate(q, q.questions.associate { it.id to v })
        assertTrue(all(1).dimensions.all { it.percent == 0 })
        assertTrue(all(3).dimensions.all { it.percent == 50 })
        assertTrue(all(5).dimensions.all { it.percent == 100 })
        assertNull(all(3).band)
        assertEquals("Dein innerer Antreiber ist spürbar aktiv", all(3).dimensions.first().band?.title)
        // nur Perfektionist hoch, Rest niedrig
        val mixed = QuestionnaireEvaluator.evaluate(q, q.questions.associate { it.id to if (it.dimension == "Perfektionist & Antreiber") 5 else 2 })
        assertEquals(listOf(100, 25, 25, 25), mixed.dimensions.map { it.percent })
        assertEquals(mapOf("Perfektionist & Antreiber" to 100, "People Pleaser & Anpasser" to 25,
            "Katastrophenplaner & Kontrolleur" to 25, "Abwerter & Zerstörer" to 25), SelfTestResults.toScores(q, mixed))
    }

    @Test
    fun scoresAreStoredRestoredAndCompared() {
        val r1 = QuestionnaireEvaluator.evaluate(twoParts, mapOf("a1" to 4, "a2" to 2, "b1" to 2, "b2" to 2))
        val scores1 = SelfTestResults.toScores(twoParts, r1)
        assertEquals(mapOf("A" to 75, "B" to 50), scores1)
        assertEquals(listOf("A hoch", "B mittel"),
            SelfTestResults.dimensionScores(twoParts, scores1).map { it.band?.title })

        val j = Journal(InMemoryJournalStore(), persistent = true)
        val day = LocalDate.of(2026, 11, 10)
        val first = j.add(EntryKind.SELF_TEST, twoParts.id, twoParts.title, day, null, scores = scores1)
        val second = j.add(EntryKind.SELF_TEST, twoParts.id, twoParts.title, day.plusDays(30), null, scores = mapOf("A" to 50, "B" to 52))
        assertNull(SelfTestResults.previous(j.entries.value, first))
        assertEquals(first.id, SelfTestResults.previous(j.entries.value, second)?.id)

        val changes = SelfTestResults.compare(first.scores, second.scores)
        assertEquals(listOf(-25, 2), changes.map { it.difference })
        assertEquals("etwas weniger ausgeprägt (-25)", SelfTestResults.describe(changes[0]))
        assertEquals("fast unverändert", SelfTestResults.describe(changes[1]))
    }

    @Test
    fun exampleKeepsOverallScore() {
        val q = SelfTestCatalog.example
        val r = QuestionnaireEvaluator.evaluate(q, q.questions.associate { it.id to 2 })
        val scores = SelfTestResults.toScores(q, r)
        assertEquals(mapOf(SelfTestResults.OVERALL to 50), scores)
        assertEquals("Mal so, mal so", SelfTestResults.overallBand(q, scores)?.title)
    }
}

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
        assertEquals(40, q.questions.size)
        q.dimensions.forEach { d ->
            assertEquals(d.name, 10, q.questions.count { it.dimension == d.name })
            assertEquals(d.name, 1, q.questions.count { it.dimension == d.name && it.reversed })
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
        fun eval(f: (de.lichtblickraum.core.selftest.Question) -> Int) = QuestionnaireEvaluator.evaluate(q, q.questions.associate { it.id to f(it) })
        // stärkste Zustimmung zum Kritiker (umgekehrte Aussagen = 1) -> 100
        assertTrue(eval { if (it.reversed) 1 else 5 }.dimensions.all { it.percent == 100 })
        assertTrue(eval { if (it.reversed) 5 else 1 }.dimensions.all { it.percent == 0 })
        // überall 3 -> 50, überall 5 -> 90 (die umgekehrte Aussage zählt dann 1)
        assertTrue(eval { 3 }.dimensions.all { it.percent == 50 })
        assertTrue(eval { 5 }.dimensions.all { it.percent == 90 })
        assertNull(eval { 3 }.band)
        assertEquals("Dein innerer Antreiber ist spürbar aktiv", eval { 3 }.dimensions.first().band?.title)
        // nur Perfektionist hoch
        val mixed = eval { if (it.dimension == "Perfektionist & Antreiber") (if (it.reversed) 1 else 5) else 2 }
        assertEquals(mapOf("Perfektionist & Antreiber" to 100, "People Pleaser & Anpasser" to 30,
            "Katastrophenplaner & Kontrolleur" to 30, "Abwerter & Zerstörer" to 30), SelfTestResults.toScores(q, mixed))
        assertEquals("Dein innerer Antreiber ist gerade sehr präsent", mixed.dimensions.first().band?.title)
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

    @Test
    fun selfRelationshipIsCompleteWithOverallAndParts() {
        val q = SelfTestCatalog.selfRelationship
        assertEquals(emptyList<String>(), QuestionnaireEvaluator.validate(q))
        assertEquals(40, q.questions.size)
        assertEquals(4, q.dimensions.size)
        q.dimensions.forEach { d ->
            assertEquals(10, q.questions.count { it.dimension == d.name })
            assertEquals(1, q.questions.count { it.dimension == d.name && it.reversed })
        }
        assertEquals(3, q.bands.size)
        fun eval(f: (de.lichtblickraum.core.selftest.Question) -> Int) =
            QuestionnaireEvaluator.evaluate(q, q.questions.associate { it.id to f(it) })
        val best = eval { if (it.reversed) 1 else 5 }
        assertEquals(100, best.percent)
        assertEquals("Du hast eine tragfähige Beziehung zu dir", best.band?.title)
        val worst = eval { if (it.reversed) 5 else 1 }
        assertEquals(0, worst.percent)
        assertEquals("Deine Beziehung zu dir braucht gerade viel Zuwendung", worst.band?.title)
        val scores = SelfTestResults.toScores(q, eval { 3 })
        assertEquals(5, scores.size)
        assertEquals(50, scores[SelfTestResults.OVERALL])
        assertEquals("Deine Beziehung zu dir ist auf einem guten Weg", SelfTestResults.overallBand(q, scores)?.title)
        assertEquals(4, SelfTestResults.dimensionScores(q, scores).size)
        val critic = SelfTestCatalog.innerCritic.questions.map { it.text }.toSet()
        assertTrue(q.questions.none { it.text in critic })
    }

    @Test
    fun emotionalDependencyIsComplete() {
        val q = SelfTestCatalog.emotionalDependency
        assertEquals(emptyList<String>(), QuestionnaireEvaluator.validate(q))
        assertEquals(40, q.questions.size)
        q.dimensions.forEach { d ->
            assertEquals(10, q.questions.count { it.dimension == d.name })
            assertEquals(1, q.questions.count { it.dimension == d.name && it.reversed })
        }
        val strongest = QuestionnaireEvaluator.evaluate(q, q.questions.associate { it.id to if (it.reversed) 1 else 5 })
        assertEquals(100, strongest.percent)
        assertEquals("Dein Gleichgewicht hängt gerade stark von anderen ab", strongest.band?.title)
        val all = (SelfTestCatalog.all.flatMap { it.questions }).map { it.text }
        assertEquals("keine doppelten Aussagen über alle Tests", all.size, all.toSet().size)
    }
}

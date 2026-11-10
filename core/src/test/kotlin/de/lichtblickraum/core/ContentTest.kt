package de.lichtblickraum.core

import de.lichtblickraum.core.about.AboutContent
import de.lichtblickraum.core.config.AppLinks
import de.lichtblickraum.core.exercises.ExerciseCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ContentTest {

    @Test
    fun exercisesAreGroupedInCategories() {
        assertEquals(de.lichtblickraum.core.exercises.ExerciseCategory.entries.toList(), ExerciseCatalog.categories)
        ExerciseCatalog.categories.forEach { c ->
            assertTrue("mind. 2 Übungen in ${c.title}", ExerciseCatalog.inCategory(c).size >= 2)
        }
        assertEquals("IDs eindeutig", ExerciseCatalog.all.size, ExerciseCatalog.all.map { it.id }.toSet().size)
        ExerciseCatalog.all.forEach { assertTrue(it.steps.isNotEmpty()) }
        // Bisherige Übungen bleiben erhalten (IDs stehen ggf. schon in der Rückschau)
        listOf("orientierung", "grenzen-reflexion", "freundlicher-dialog").forEach {
            assertTrue(ExerciseCatalog.byId(it) != null)
        }
    }

    @Test
    fun linkCheck() {
        assertFalse(AppLinks.isUsable(""))
        assertFalse(AppLinks.isUsable("http://unsicher.de"))
        assertTrue(AppLinks.isUsable(AppLinks.WEBSITE_URL))
        assertFalse(AboutContent.isPlaceholder(AboutContent.INTRODUCTION))
        assertTrue(AboutContent.qualifications.none { AboutContent.isPlaceholder(it) })
        assertTrue(AppLinks.isBookingConfigured)
    }

    @Test
    fun noForbiddenWording() {
        val texts = buildString {
            de.lichtblickraum.core.impulses.DailyImpulses.all.forEach { append(it.task).append(it.explanation) }
            de.lichtblickraum.core.impulses.WeeklyImpulses.all.forEach { append(it.task).append(it.explanation) }
            ExerciseCatalog.all.forEach { e -> append(e.intro); e.steps.forEach { append(it.text) } }
        }.lowercase()
        listOf("heilt", "heilung", "garantiert", "streak", "serie verloren", "du musst unbedingt")
            .forEach { assertFalse("Unerwünschte Formulierung: $it", texts.contains(it)) }
    }
}

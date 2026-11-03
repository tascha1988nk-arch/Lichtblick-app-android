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
    fun threeExercisesWithSteps() {
        assertEquals(3, ExerciseCatalog.all.size)
        ExerciseCatalog.all.forEach { assertTrue(it.steps.isNotEmpty()) }
    }

    @Test
    fun linkCheck() {
        assertFalse(AppLinks.isUsable(""))
        assertFalse(AppLinks.isUsable("http://unsicher.de"))
        assertTrue(AppLinks.isUsable(AppLinks.WEBSITE_URL))
        assertTrue(AboutContent.isPlaceholder(AboutContent.INTRODUCTION))
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

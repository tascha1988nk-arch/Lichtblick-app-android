package de.lichtblickraum.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.lichtblickraum.core.profile.Focus
import de.lichtblickraum.core.thoughts.DailyThoughts
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/** Eingangsfragen beim ersten Start. */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [34], qualifiers = "w411dp-h891dp-xxhdpi")
class OnboardingTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private val container get() = (rule.activity.application as LichtblickApplication).container

    private fun clickText(text: String) {
        rule.onNodeWithText(text).performScrollTo().performClick()
        rule.waitForIdle()
    }

    @Test
    fun answersPersonaliseStartPage() {
        rule.onNodeWithText("Willkommen bei Lichtblick").assertExists()
        clickText("Los geht's")
        rule.onNodeWithText("Dein Name (freiwillig)").performTextInput("Mia")
        clickText("Weiter")
        clickText("ADHS im Alltag")
        clickText("Weiter")
        rule.onNodeWithText("z. B. „Ich möchte freundlicher mit mir sein.“").performTextInput("Mehr Ruhe im Kopf")
        clickText("Weiter")
        clickText("Fertig")

        assertEquals(setOf(Focus.ADHD), container.profileStore.profile.value.focus)
        assertEquals("Mia", container.profileStore.profile.value.name)
        rule.onAllNodesWithText(", Mia.", substring = true).onFirst().assertExists()
        rule.onAllNodesWithText("Dein Kompass: „Mehr Ruhe im Kopf“").onFirst().assertExists()
        rule.onAllNodesWithText(DailyThoughts.forDay(container.today(), setOf(Focus.ADHD)).text).onFirst().assertExists()
    }

    @Test
    fun onboardingCanBeSkipped() {
        clickText("Überspringen")
        rule.onAllNodesWithText("Schön, dass du da bist.", substring = true).onFirst().assertExists()
        assertEquals(emptySet<Focus>(), container.profileStore.profile.value.focus)
    }

    @Test
    fun themesCanBeChangedLater() {
        clickText("Überspringen")
        clickText("Deine Themen anpassen")
        clickText("Weiter")
        clickText("Grenzen")
        clickText("Weiter")
        clickText("Weiter")
        clickText("Speichern")
        assertEquals(setOf(Focus.BOUNDARIES), container.profileStore.profile.value.focus)
    }
}

package de.lichtblickraum.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.lichtblickraum.app.navigation.Routes
import de.lichtblickraum.core.impulses.Feedback
import de.lichtblickraum.core.selftest.SelfTestCatalog
import android.content.Intent
import de.lichtblickraum.core.config.AppLinks
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.robolectric.Shadows
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Oberflächentests, die ohne Handy auf dem Rechner laufen (Robolectric).
 * Sie prüfen Navigation, Fragebogenablauf, Ergebnis und die kleinen Lichtblicke.
 */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [34], qualifiers = "w411dp-h891dp-xxhdpi")
class AppFlowTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private fun clickText(text: String) {
        rule.onNodeWithText(text).performScrollTo().performClick()
        rule.waitForIdle()
    }

    private fun clickNav(route: String) {
        rule.onNodeWithTag("nav_$route").performClick()
        rule.waitForIdle()
    }

    private fun assertTextShown(text: String, substring: Boolean = false) {
        rule.onAllNodesWithText(text, substring = substring).onFirst().assertExists()
    }

    @Test
    fun startScreenShowsGreetingAndEntries() {
        assertTextShown("Schön, dass du da bist.", substring = true)
        assertTextShown("Kleine Lichtblicke")
        assertTextShown("Selbsttests")
        assertTextShown("Übungen")
        assertTextShown("Über Lichtblick")
        assertTextShown("Termin vereinbaren")
    }

    @Test
    fun bottomNavigationReachesAllAreas() {
        clickNav(Routes.SELF_TESTS)
        assertTextShown("Zum Ausprobieren")
        clickNav(Routes.EXERCISES)
        assertTextShown("Ankommen im Hier und Jetzt")
        clickNav(Routes.LICHTBLICKE)
        assertTextShown("Dein Lichtblick für heute")
        clickNav(Routes.ABOUT)
        assertTextShown("Schwerpunkte")
        clickNav(Routes.HOME)
        assertTextShown("Schön, dass du da bist.", substring = true)
    }

    @Test
    fun homeCardsOpenSections() {
        clickText("Kurze Übungen für stressige Momente, Grenzen und einen freundlicheren inneren Dialog.")
        assertTextShown("Meine Grenzen wahrnehmen")
    }

    @Test
    fun questionnaireCompleteFlowShowsResult() {
        clickNav(Routes.SELF_TESTS)
        clickText(SelfTestCatalog.example.title)
        clickText("Test beginnen")

        val count = SelfTestCatalog.example.questions.size
        for (i in 1..count) {
            assertTextShown("Frage $i von $count")
            rule.onNodeWithText(if (i == count) "Zur Auswertung" else "Weiter").assertIsNotEnabled()
            clickText("Trifft voll zu")
            rule.onNodeWithText(if (i == count) "Zur Auswertung" else "Weiter").assertIsEnabled()
            clickText(if (i == count) "Zur Auswertung" else "Weiter")
        }
        // 6 normale Aussagen * 4 + 2 umgepolte * 0 = 75 % -> oberer Bereich
        assertTextShown("Oft gehst du schon freundlich mit dir um")
        assertTextShown("BEISPIEL-AUSWERTUNG", substring = true)
        assertTextShown("Dieses Ergebnis wird nicht gespeichert", substring = true)

        clickText("Fertig")
        assertTextShown("Zum Ausprobieren")
    }

    @Test
    fun backInQuestionnaireKeepsAnswer() {
        clickNav(Routes.SELF_TESTS)
        clickText(SelfTestCatalog.example.title)
        clickText("Test beginnen")
        clickText("Teils, teils")
        clickText("Weiter")
        assertTextShown("Frage 2 von 8")
        clickText("Zurück")
        assertTextShown("Frage 1 von 8")
        rule.onNodeWithText("Weiter").assertIsEnabled()
    }

    @Test
    fun leavingTestAsksAndDiscardsAnswers() {
        clickNav(Routes.SELF_TESTS)
        clickText(SelfTestCatalog.example.title)
        clickText("Test beginnen")
        clickText("Trifft eher zu")
        clickText("Weiter")

        rule.onNodeWithContentDescription("Test verlassen").performClick()
        rule.waitForIdle()
        rule.onNodeWithText("Test verlassen?").assertIsDisplayed()
        rule.onNodeWithText("Verlassen").performClick()
        rule.waitForIdle()
        assertTextShown("Zum Ausprobieren")

        // Neu starten: Antworten sind verworfen
        clickText(SelfTestCatalog.example.title)
        clickText("Test beginnen")
        assertTextShown("Frage 1 von 8")
        rule.onNodeWithText("Weiter").assertIsNotEnabled()
    }

    @Test
    fun inPreparationTestsAreMarked() {
        clickNav(Routes.SELF_TESTS)
        assertTextShown("In Vorbereitung")
        assertTextShown("Innerer Kritiker")
        assertTextShown("Folgt bald")
    }

    @Test
    fun exerciseCanBeStartedAndEndedAnytime() {
        clickNav(Routes.EXERCISES)
        clickText("Ankommen im Hier und Jetzt")
        clickText("Übung beginnen")
        assertTextShown("Schritt 1 von 5")
        clickText("Übung beenden")
        assertTextShown("Meine Grenzen wahrnehmen")
    }

    @Test
    fun exerciseCompletionAppearsInReview() {
        clickNav(Routes.EXERCISES)
        clickText("Ein freundlicherer innerer Dialog")
        clickText("Übung beginnen")
        repeat(3) { clickText("Weiter") }
        clickText("Zum Abschluss")
        clickText("In der Rückschau vermerken")
        assertTextShown("Vermerkt")
        clickText("Zurück zu den Übungen")
        clickNav(Routes.LICHTBLICKE)
        clickText("Rückschau ansehen")
        assertTextShown("Ein freundlicherer innerer Dialog")
    }

    @Test
    fun dailyImpulseDoneShowsFeedbackAndReviewEntry() {
        clickNav(Routes.LICHTBLICKE)
        rule.onAllNodesWithText("Für mich gemacht").onFirst().performScrollTo().performClick()
        rule.waitForIdle()
        val feedbackShown = Feedback.afterDone.any {
            rule.onAllNodesWithText(it).fetchSemanticsNodes().isNotEmpty()
        }
        assertTrue("Freundliche Rückmeldung erwartet", feedbackShown)

        clickText("Rückschau ansehen")
        assertTextShown("Lichtblick für heute")
        assertTextShown("Einträge auf diesem Gerät speichern")
    }

    @Test
    fun skipAndPauseAreGentle() {
        clickNav(Routes.LICHTBLICKE)
        clickText("Heute auslassen")
        assertTextShown(Feedback.AFTER_SKIP)
        clickText("Doch anschauen")
        rule.onAllNodesWithText("Für mich gemacht").onFirst().assertExists()

        clickText("Kleine Lichtblicke pausieren")
        assertTextShown("Pause")
        clickText("Wieder einsteigen")
        assertTextShown(Feedback.WELCOME_BACK)
        assertTextShown("Dein Lichtblick für heute")
    }

    @Test
    fun bookingOpensAppointmentPageInBrowser() {
        clickText("Termin vereinbaren")
        val intent = Shadows.shadowOf(rule.activity.application).nextStartedActivity
        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals(AppLinks.BOOKING_URL, intent.dataString)
    }

    @Test
    fun aboutShowsPortraitAndOffers() {
        clickNav(Routes.ABOUT)
        rule.onNodeWithContentDescription("Porträtfoto").assertExists()
        assertTextShown("Paarberatung")
        assertTextShown("Psychologisches Einzelcoaching")
    }

    @Test
    fun legalPagesShowPlaceholders() {
        clickNav(Routes.ABOUT)
        clickText("Impressum")
        assertTextShown("Entwurf", substring = true)
        rule.onNodeWithContentDescription("Zurück").performClick()
        rule.waitForIdle()
        clickText("Datenschutzhinweise")
        assertTextShown("Kurz gesagt")
    }
}

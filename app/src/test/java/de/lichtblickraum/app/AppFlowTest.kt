package de.lichtblickraum.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertCountEquals
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
import android.Manifest
import android.content.Intent
import de.lichtblickraum.core.config.AppLinks
import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.thoughts.DailyThoughts
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.robolectric.Shadows
import org.junit.Before
import org.junit.Rule
import de.lichtblickraum.core.profile.UserProfile
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

    private val container get() = (rule.activity.application as LichtblickApplication).container

    @Before
    fun skipOnboarding() {
        rule.runOnIdle { container.saveProfile(UserProfile()) }
        rule.waitForIdle()
    }

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
        assertTextShown("Paarthemen")
        clickNav(Routes.LICHTBLICKE)
        assertTextShown("Dein Lichtblick für heute")
        clickNav(Routes.ABOUT)
        assertTextShown("Schwerpunkte")
        clickNav(Routes.HOME)
        assertTextShown("Schön, dass du da bist.", substring = true)
    }

    @Test
    fun homeCardsOpenSections() {
        clickText("Kurze Übungen zu Selbstwert, Grenzen, Paarthemen, ADHS im Alltag sowie Gefühlen und Stress.")
        assertTextShown("ADHS im Alltag")
    }

    @Test
    fun questionnaireCompleteFlowShowsResult() {
        clickNav(Routes.SELF_TESTS)
        clickText(SelfTestCatalog.innerCritic.title)
        clickText("Test beginnen")

        val count = SelfTestCatalog.innerCritic.questions.size
        for (i in 1..count) {
            assertTextShown("Frage $i von $count")
            rule.onNodeWithText(if (i == count) "Zur Auswertung" else "Weiter").assertIsNotEnabled()
            clickText("5 · Stimmt total")
            rule.onNodeWithText(if (i == count) "Zur Auswertung" else "Weiter").assertIsEnabled()
            clickText(if (i == count) "Zur Auswertung" else "Weiter")
        }
        // alle 5 -> jeder Bereich 90 (je eine umgekehrte Aussage) -> obere Stufe
        assertTextShown("Dein innerer Antreiber ist gerade sehr präsent")
        assertTextShown("Perfektionist & Antreiber · 90 / 100")
        assertTextShown("Dein Erlauber:")
        assertTextShown("Reflexionsfrage:")
        assertTextShown("Möchtest du genauer hinschauen?")
        assertTextShown("Dein Ergebnis ist auf diesem Gerät gespeichert", substring = true)

        clickText("Fertig")
        assertTextShown("Zum Ausprobieren")
    }

    private fun answerAll(option: String) {
        val count = SelfTestCatalog.innerCritic.questions.size
        for (i in 1..count) {
            clickText(option)
            clickText(if (i == count) "Zur Auswertung" else "Weiter")
        }
    }

    @Test
    fun repeatedTestIsComparedWithPreviousResult() {
        clickNav(Routes.SELF_TESTS)
        clickText(SelfTestCatalog.innerCritic.title)
        clickText("Test beginnen")
        answerAll("5 · Stimmt total")        // 90
        clickText("Test noch einmal machen")
        clickText("Test beginnen")
        answerAll("3 · Teils, teils")        // 50
        assertTextShown("Im Vergleich zu deinem letzten Ergebnis")
        assertTextShown("90 → 50", substring = true)
        clickText("Fertig")
        assertTextShown("Deine Ergebnisse")
        rule.onAllNodesWithText("ansehen", substring = true).assertCountEquals(2)
        rule.onAllNodesWithText("ansehen", substring = true).onFirst().performScrollTo().performClick()
        rule.waitForIdle()
        assertTextShown("Dein innerer Antreiber ist spürbar aktiv")
    }

    @Test
    fun selfRelationshipShowsOverallAndParts() {
        val q = SelfTestCatalog.selfRelationship
        clickNav(Routes.SELF_TESTS)
        clickText(q.title)
        clickText("Test beginnen")
        for (i in 1..q.questions.size) {
            clickText("3 · Teils, teils")
            clickText(if (i == q.questions.size) "Zur Auswertung" else "Weiter")
        }
        assertTextShown("Gesamtwert: 50 / 100")
        assertTextShown("Deine Beziehung zu dir ist auf einem guten Weg")
        assertTextShown("Deine Bereiche")
        assertTextShown("Am stärksten ist gerade:", substring = true)
        assertTextShown("Selbstmitgefühl · 50 / 100")
    }

    @Test
    fun backInQuestionnaireKeepsAnswer() {
        clickNav(Routes.SELF_TESTS)
        clickText(SelfTestCatalog.innerCritic.title)
        clickText("Test beginnen")
        clickText("3 · Teils, teils")
        clickText("Weiter")
        assertTextShown("Frage 2 von 40")
        clickText("Zurück")
        assertTextShown("Frage 1 von 40")
        rule.onNodeWithText("Weiter").assertIsEnabled()
    }

    @Test
    fun leavingTestAsksAndDiscardsAnswers() {
        clickNav(Routes.SELF_TESTS)
        clickText(SelfTestCatalog.innerCritic.title)
        clickText("Test beginnen")
        clickText("4 · Stimmt eher")
        clickText("Weiter")

        rule.onNodeWithContentDescription("Test verlassen").performClick()
        rule.waitForIdle()
        rule.onNodeWithText("Test verlassen?").assertIsDisplayed()
        rule.onNodeWithText("Verlassen").performClick()
        rule.waitForIdle()
        assertTextShown("Zum Ausprobieren")

        // Neu starten: Antworten sind verworfen
        clickText(SelfTestCatalog.innerCritic.title)
        clickText("Test beginnen")
        assertTextShown("Frage 1 von 40")
        rule.onNodeWithText("Weiter").assertIsNotEnabled()
    }

    @Test
    fun everyExerciseCategoryOpensWithItsExercises() {
        clickNav(Routes.EXERCISES)
        de.lichtblickraum.core.exercises.ExerciseCatalog.categories.forEach { category ->
            clickText(category.title)
            de.lichtblickraum.core.exercises.ExerciseCatalog.inCategory(category).forEach { assertTextShown(it.title) }
            rule.onNodeWithContentDescription("Zurück").performClick()
            rule.waitForIdle()
        }
        clickText("Paarthemen")
        assertTextShown("Diese Übungen machst du für dich allein.", substring = true)
    }

    @Test
    fun inPreparationTestsAreMarked() {
        clickNav(Routes.SELF_TESTS)
        assertTextShown("In Vorbereitung")
        assertTextShown("Was ist meine Liebessprache?")
        assertTextShown("Folgt bald")
    }

    @Test
    fun exerciseCanBeStartedAndEndedAnytime() {
        clickNav(Routes.EXERCISES)
        clickText("Gefühle & Stress")
        clickText("Ankommen im Hier und Jetzt")
        clickText("Übung beginnen")
        assertTextShown("Schritt 1 von 5")
        clickText("Übung beenden")
        assertTextShown("Ein Gefühl benennen und begleiten")
    }

    @Test
    fun exerciseCompletionAppearsInReview() {
        clickNav(Routes.EXERCISES)
        clickText("Selbstwert")
        clickText("Ein freundlicherer innerer Dialog")
        clickText("Übung beginnen")
        repeat(3) { clickText("Weiter") }
        clickText("Zum Abschluss")
        clickText("In der Rückschau vermerken")
        assertTextShown("Vermerkt")
        clickText("Zurück zur Übersicht")
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
        // Speichern ist von Anfang an eingeschaltet, mit Hinweis zum Ausschalten
        rule.onNodeWithText("Einträge auf diesem Gerät speichern").assertIsOn()
        assertTextShown("Du kannst das Speichern hier jederzeit ausschalten.", substring = true)
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
    fun remindersAreOffByDefaultAndCanBeSwitchedOn() {
        Shadows.shadowOf(rule.activity.application).grantPermissions(Manifest.permission.POST_NOTIFICATIONS)
        clickNav(Routes.LICHTBLICKE)
        assertTextShown("Sanfte Erinnerungen")
        rule.onNodeWithText("Täglich an deinen Lichtblick erinnern").performScrollTo().assertIsOff()
        rule.onAllNodesWithText("Uhrzeit:", substring = true).assertCountEquals(0)

        clickText("Täglich an deinen Lichtblick erinnern")
        rule.onNodeWithText("Täglich an deinen Lichtblick erinnern").assertIsOn()
        assertTextShown("Uhrzeit: 09:00 Uhr")

        clickText("Wöchentlich an deinen Wochenimpuls erinnern")
        assertTextShown("Tag: Sonntag")
        assertTextShown("Uhrzeit: 18:00 Uhr")

        clickText("Täglich an deinen Lichtblick erinnern")
        rule.onNodeWithText("Täglich an deinen Lichtblick erinnern").assertIsOff()
    }

    @Test
    fun moodCheckInFromHomeIsSaved() {
        rule.onNodeWithContentDescription("Eher gut").performScrollTo().performClick()
        rule.waitForIdle()
        assertTextShown("Welche Gefühle sind da? (freiwillig)")
        clickText("ruhig")
        clickText("Speichern")
        assertTextShown("Danke, dass du hingespürt hast")
        clickText("Fertig")
        assertTextShown("Heute eingetragen:", substring = true)
        clickNav(Routes.LICHTBLICKE)
        clickText("Rückschau ansehen")
        assertTextShown("Gefühlsbarometer")
        assertTextShown("ruhig")
    }

    @Test
    fun supportHintAfterSeveralHardDays() {
        rule.runOnIdle {
            val today = container.today()
            (0L..4L).forEach { container.journal.add(EntryKind.MOOD, "barometer", "Gefühlsbarometer", today.minusDays(it), null, level = 1) }
        }
        rule.waitForIdle()
        assertTextShown("TelefonSeelsorge anrufen: 116 123")
        clickText("Danke, jetzt nicht")
        rule.onAllNodesWithText("TelefonSeelsorge anrufen: 116 123").assertCountEquals(0)
    }

    @Test
    fun diaryEntryAppearsInReview() {
        clickText("Gefühlstagebuch")
        rule.onNodeWithText("Speichern").assertIsNotEnabled()
        rule.onNodeWithText("Was ist passiert?").performScrollTo().performTextInput("Ein voller Tag")
        clickText("Speichern")
        assertTextShown("Festgehalten")
    }

    @Test
    fun homeShowsThoughtLichtblickAndExerciseForToday() {
        assertTextShown("Für heute")
        assertTextShown(DailyThoughts.forDay(container.today(), emptySet()).text)
        assertTextShown("Dein Lichtblick für heute")
        assertTextShown("Eine Übung für dich")
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
        assertTextShown("Natascha Helbig")
        assertTextShown("Kennenlerngespräch")
        assertTextShown("Zeit für dich")
    }

    @Test
    fun legalPagesShowPlaceholders() {
        clickNav(Routes.ABOUT)
        clickText("Impressum")
        assertTextShown("Kurbrunnenstraße 11")
        assertTextShown("Übernommen aus dem Impressum", substring = true)
        rule.onNodeWithContentDescription("Zurück").performClick()
        rule.waitForIdle()
        clickText("Datenschutzhinweise")
        assertTextShown("Kurz gesagt")
        assertTextShown("Entwurf", substring = true)
    }
}

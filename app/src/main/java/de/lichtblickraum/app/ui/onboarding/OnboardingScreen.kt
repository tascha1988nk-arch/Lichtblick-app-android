package de.lichtblickraum.app.ui.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickLogo
import de.lichtblickraum.app.ui.components.LichtblickTopBar
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.PrimaryButton
import de.lichtblickraum.app.ui.components.QuietButton
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.components.TouchHeight
import de.lichtblickraum.app.ui.exercises.NoteField
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.about.AboutContent
import de.lichtblickraum.core.profile.Focus
import de.lichtblickraum.core.profile.TimePreference

/**
 * Eingangsfragen: beim ersten Start (editMode = false) oder später über "Deine Themen anpassen".
 * Jede Frage ist freiwillig; "Überspringen" ist immer möglich.
 */
@Composable
fun OnboardingScreen(container: AppContainer, editMode: Boolean, onDone: () -> Unit) {
    val current = container.profileStore.profile.value
    var page by remember { mutableIntStateOf(if (editMode) 1 else 0) }
    var name by remember { mutableStateOf(current.name.orEmpty()) }
    var focus by remember { mutableStateOf(current.focus) }
    var wish by remember { mutableStateOf(current.wish.orEmpty()) }
    var time by remember { mutableStateOf(current.time) }
    val lastPage = 4

    fun finish() {
        container.saveProfile(current.copy(name = name.trim().ifEmpty { null }, focus = focus, wish = wish.trim().ifEmpty { null }, time = time))
        onDone()
    }

    fun skip() {
        if (!editMode) container.saveProfile(current)
        onDone()
    }

    BackHandler(enabled = page > (if (editMode) 1 else 0)) { page-- }

    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = {
            if (editMode) LichtblickTopBar(title = "Deine Themen", onBack = onDone)
        },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                if (page > 0) {
                    Text("Frage $page von $lastPage", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
                    LinearProgressIndicator(
                        progress = { page / lastPage.toFloat() },
                        modifier = Modifier.fillMaxWidth(),
                        color = LichtblickColors.GoldDark,
                        trackColor = LichtblickColors.GoldSoft,
                    )
                }
                when (page) {
                    0 -> {
                        LichtblickLogo(Modifier.align(Alignment.CenterHorizontally))
                        Heading("Willkommen bei ${AboutContent.PRACTICE_NAME}")
                        BodyText(
                            "Magst du mir kurz verraten, was dir gerade wichtig ist? Dann kann ich dir " +
                                "passende Gedanken, Lichtblicke und Übungen zeigen.",
                        )
                        NoteBox(
                            text = "Vier kurze Fragen, ganz freiwillig. Das ist kein Test und keine Diagnose. " +
                                "Deine Antworten bleiben auf deinem Gerät, und du kannst sie jederzeit ändern.",
                        )
                        PrimaryButton("Los geht's", { page = 1 })
                        QuietButton("Überspringen", ::skip)
                    }
                    1 -> {
                        Heading("Wie darf ich dich ansprechen?")
                        BodyText(
                            "Wenn du magst, schreib deinen Vornamen oder einen Namen, mit dem du gern " +
                                "begrüßt werden möchtest. Du kannst das Feld auch leer lassen.",
                            color = LichtblickColors.TextMuted,
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it.take(40) },
                            label = { Text("Dein Name (freiwillig)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = LichtblickColors.GoldDark,
                                unfocusedBorderColor = LichtblickColors.Gold,
                                focusedLabelColor = LichtblickColors.GoldDark,
                                cursorColor = LichtblickColors.GoldDark,
                                focusedContainerColor = LichtblickColors.CreamLight,
                                unfocusedContainerColor = LichtblickColors.CreamLight,
                            ),
                        )
                        PrimaryButton("Weiter", { page = 2 })
                    }
                    2 -> {
                        Heading("Was ist dir gerade wichtig?")
                        BodyText("Du kannst mehrere Themen wählen – oder keines.", color = LichtblickColors.TextMuted)
                        Focus.entries.forEach { f ->
                            val checked = f in focus
                            ChoiceCard(selected = checked) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = TouchHeight)
                                        .toggleable(value = checked, role = Role.Checkbox, onValueChange = {
                                            focus = if (it) focus + f else focus - f
                                        })
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Checkbox(
                                        checked = checked,
                                        onCheckedChange = null,
                                        colors = CheckboxDefaults.colors(checkedColor = LichtblickColors.GoldDark, uncheckedColor = LichtblickColors.Gold),
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Column {
                                        Text(f.title, style = MaterialTheme.typography.titleMedium)
                                        Text(f.description, style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                                    }
                                }
                            }
                        }
                        PrimaryButton("Weiter", { page = 3 })
                        QuietButton("Zurück", { page = 1 })
                    }
                    3 -> {
                        Heading("Was wünschst du dir?")
                        BodyText(
                            "Wenn du magst, beschreib in deinen Worten, wohin es für dich gehen soll. " +
                                "Dein Wunsch erscheint als kleiner Kompass auf der Startseite.",
                            color = LichtblickColors.TextMuted,
                        )
                        NoteField(value = wish, onValueChange = { wish = it }, label = "z. B. „Ich möchte freundlicher mit mir sein.“")
                        PrimaryButton("Weiter", { page = 4 })
                        QuietButton("Zurück", { page = 2 })
                    }
                    4 -> {
                        Heading("Wie viel Zeit hast du meistens?")
                        TimePreference.entries.forEach { t ->
                            val selected = t == time
                            ChoiceCard(selected = selected) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = TouchHeight)
                                        .selectable(selected = selected, role = Role.RadioButton, onClick = { time = t })
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    RadioButton(
                                        selected = selected,
                                        onClick = null,
                                        colors = RadioButtonDefaults.colors(selectedColor = LichtblickColors.GoldDark, unselectedColor = LichtblickColors.Gold),
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Text(t.label, style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                        }
                        BodyText(
                            "Es gibt kein Programm und nichts zu verpassen. Schau einfach vorbei, wann immer du magst.",
                            color = LichtblickColors.TextMuted,
                        )
                        PrimaryButton(if (editMode) "Speichern" else "Fertig", ::finish)
                        QuietButton("Zurück", { page = 3 })
                    }
                }
            }
        }
    }
}

@Composable
private fun ChoiceCard(selected: Boolean, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) LichtblickColors.GoldSoft else LichtblickColors.CreamLight,
            contentColor = LichtblickColors.Text,
        ),
        border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) LichtblickColors.GoldDark else LichtblickColors.GoldSoft),
    ) { content() }
}

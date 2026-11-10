# Lichtblick – Android-App

App zur psychologischen Beratung **Lichtblick** in Bad Dürkheim.
Native Android-App mit **Kotlin** und **Jetpack Compose**. Stand: Prototyp, Version 0.1.

## Was die App enthält

| Bereich | Inhalt |
|---|---|
| **Start** | Begrüßung, Zugänge zu allen Bereichen, „Termin vereinbaren“, Hinweis für Krisen |
| **Selbsttests** | Beispiel-Fragebogen (deutlich als unverbindliche Reflexion gekennzeichnet) mit Fortschritt und Auswertung; „Innerer Kritiker“, „People Pleasing“, „Selbstwert“ und „Grenzen“ sind als „In Vorbereitung“ angelegt |
| **Übungen** | Ankommen im Hier und Jetzt · Meine Grenzen wahrnehmen · Ein freundlicherer innerer Dialog |
| **Lichtblicke** | „Dein Lichtblick für heute“ (60 Impulse), „Dein Wochenimpuls“ (9 Impulse), Rückschau, Pause. Jede Runde neu gemischt; kürzlich Erledigtes (3 Wochen bzw. 4 Wochen) wird übersprungen. Freiwillige Erinnerungen (täglich/wöchentlich, Uhrzeit wählbar, standardmäßig aus) |
| **Über** | Platz für Foto, Vorstellung und Angebote, Schwerpunkte, Website, Impressum, Datenschutz |

**Datenschutz im Prototyp:** keine Registrierung, keine Werbung, kein Tracking, keine Internet-Berechtigung.
Antworten aus Selbsttests werden nie gespeichert. Einträge aus Lichtblicken und Übungen werden standardmäßig
nur auf dem Gerät gespeichert. Das lässt sich in der Rückschau jederzeit ausschalten. Einträge sind
einzeln oder vollständig löschbar. Android-Sicherungen sind ausgeschlossen.
**Die App ist nicht rechtlich geprüft.**

## Wo du Inhalte änderst

Alle Inhalte liegen getrennt von der Oberfläche im Ordner `core/src/main/kotlin/de/lichtblickraum/core/`:

| Was | Datei |
|---|---|
| **Termin-Link** (Seite „Termin vereinbaren“ auf der Website) und Website | `config/AppLinks.kt` → `BOOKING_URL` |
| Vorstellung, Name, Angebote, Begrüßung | `about/AboutContent.kt` |
| Impressum, Datenschutz, Hinweise, Krisennummern | `about/LegalContent.kt` |
| Selbsttests: Fragen, Antwortskala, Auswertungsbereiche | `selftest/SelfTestCatalog.kt` |
| Übungen | `exercises/ExerciseCatalog.kt` |
| Tages- / Wochenimpulse | `impulses/DailyImpulses.kt`, `impulses/WeeklyImpulses.kt` |
| Rückmeldungen nach einem Impuls | `impulses/Feedback.kt` |
| Farben | `app/src/main/java/de/lichtblickraum/app/ui/theme/Color.kt` |
| Foto | `app/src/main/res/drawable-nodpi/portrait.jpg` (quadratisch, Gesicht mittig) |

Alles, was noch fehlt, ist mit `[Platzhalter …]` markiert. In der App erscheint es farbig hervorgehoben.

**Einen neuen Selbsttest ergänzen:** In `SelfTestCatalog.kt` bei einem vorbereiteten Test
`status = QuestionnaireStatus.AVAILABLE` setzen und `options`, `questions` und `bands` (Auswertungsbereiche
in Prozent, lückenlos von 0 bis 100) füllen. Ein automatischer Test prüft, ob die Bereiche lückenlos sind
und sich nicht überschneiden. Umgepolte Aussagen werden mit `reversed = true` markiert, Teilbereiche mit `dimension = "…"`.

**Erinnerungen:** Texte und Standardzeiten in `reminders/Reminders.kt`. Eine Erinnerung erscheint nur, wenn der Impuls noch offen ist und die Lichtblicke nicht pausiert sind.

## Projektaufbau

```
core/   Inhalte + Logik in reinem Kotlin (Auswertung, Impulsauswahl, Rückschau) – mit Unit-Tests
app/    Android-Oberfläche (Jetpack Compose), Navigation, Speicherung auf dem Gerät – mit Oberflächentests
.github/workflows/android.yml   baut auf GitHub automatisch eine Debug-APK und führt alle Tests aus
```

## Die App auf deinem Pixel 9 Pro ausprobieren

### Weg A – Mit Android Studio (empfohlen, funktioniert immer)

1. **Android Studio installieren:** Auf <https://developer.android.com/studio> herunterladen und mit den
   Standardeinstellungen installieren. Beim ersten Start lädt es das Android SDK automatisch (kostenlos).
2. **Projekt öffnen:** *File → New → Project from Version Control* und die GitHub-Adresse dieses Repositorys
   eingeben. Alternativ die ZIP-Datei entpacken und mit *File → Open* den Ordner öffnen.
   Danach unten rechts warten, bis „Gradle sync“ fertig ist. Das kann beim ersten Mal 5–10 Minuten dauern.
3. **Entwickleroptionen am Pixel einschalten:** *Einstellungen → Über das Telefon* → 7-mal auf
   **Build-Nummer** tippen und die PIN eingeben.
4. **USB-Debugging einschalten:** *Einstellungen → System → Entwickleroptionen → USB-Debugging* aktivieren.
5. **Pixel per USB-C-Kabel verbinden** und auf dem Handy „USB-Debugging zulassen“ bestätigen.
6. In Android Studio oben in der Geräteliste **Pixel 9 Pro** auswählen und auf den grünen **▶ Run**-Knopf
   klicken. Die App startet auf deinem Handy.

### Weg B – Fertige Debug-APK von GitHub

1. Auf GitHub im Repository den Reiter **Actions** öffnen und den neuesten Lauf „Android-Build“ anklicken.
2. Unten unter **Artifacts** auf **lichtblick-debug-apk** klicken. Es lädt eine ZIP-Datei herunter.
3. Die ZIP entpacken. Darin liegt `app-debug.apk`. Kopiere sie aufs Handy, z. B. über Google Drive oder per Kabel.
4. Am Pixel die Datei antippen. Wenn Android fragt, erlaubst du für diese eine App (z. B. „Dateien“)
   **„Aus dieser Quelle installieren“**.
5. Google Play Protect kann warnen, weil die App nicht aus dem Play Store kommt. Für deinen eigenen
   Testbuild kannst du „Trotzdem installieren“ wählen.

## Vor einer Veröffentlichung im Play Store (noch nicht erledigt)

- Platzhalter füllen: Name, Vorstellung, Beschreibungen der Angebote, Impressum, Datenschutz
- Impressum und Datenschutzhinweise fachkundig prüfen lassen
- Eigene Fragen und Auswertungsregeln für die Selbsttests einsetzen und den Beispiel-Fragebogen entfernen
- Krisen-Telefonnummern in `LegalContent.kt` noch einmal prüfen
- App-Kennung `de.lichtblickraum.app` in `app/build.gradle.kts` bestätigen. Sie lässt sich nach der ersten
  Veröffentlichung nicht mehr ändern.
- App-Symbol durch dein Logo ersetzen (`app/src/main/res/drawable/ic_launcher_foreground.xml`)
- Signierte Release-Version erstellen, Google-Play-Entwicklerkonto einrichten (kostenpflichtig, einmalige Gebühr),
  Datensicherheits-Angaben in der Play Console ausfüllen

## Für Entwickler

```
./gradlew :app:assembleDebug          # Debug-APK → app/build/outputs/apk/debug/
./gradlew :core:test                  # Logik-Tests
./gradlew :app:testDebugUnitTest      # Oberflächentests (Robolectric)
```
Voraussetzungen: JDK 17+, Android SDK (compileSdk 36). Gradle 8.14.3, AGP 8.10.1, Kotlin 2.1.21.

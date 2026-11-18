# Checkliste: Homepage ändern nach Eintragung und Kontoeröffnung

Stand: 18.11.2026. Abhaken mit `[x]`. Dateipfade beziehen sich auf den Ordner `malva-website/`.

Reihenfolge: **A** sofort nach der Eintragung ins Vereinsregister · **B** sobald das Vereinskonto besteht · **C** sobald die Gemeinnützigkeit anerkannt ist (Bescheid des Finanzamts) · **D** sobald der Online-Antrag startet.

---

## A · Nach Eintragung ins Vereinsregister

### Zentrale Einstellungen – `src/_data/site.js`
- [ ] `const eingetragen = false;` auf **`true`** setzen → überall steht dann „Malva e. V.“ statt „Malva (i. Gr.)“ / „Malva – Verein in Gründung“ (Header-Titel, Footer, Startseiten-Badge, Impressum, Copyright).
- [ ] Impressum-Daten eintragen:
  - [ ] `vorstand:` vertretungsberechtigter Vorstand (Namen)
  - [ ] `registergericht:` z. B. „Amtsgericht Ludwigshafen am Rhein“ (zuständiges Gericht prüfen)
  - [ ] `registernummer:` z. B. „VR 12345“
  - [ ] Anschrift prüfen (aktuell Kurbrunnenstraße 11, 67098 Bad Dürkheim) – stimmt sie mit der Anmeldung beim Registergericht überein?
- [ ] Fahrplan (`fahrplan:`) aktualisieren: Satzung, Gründungsversammlung, Eintragung → `status: "erledigt"`; Gemeinnützigkeit ggf. `"in Arbeit"`.

### Team – `src/team.njk`
- [ ] Rollen: „(geplant)“ entfernen, gewählte Ämter eintragen (Jennifer Rebelein: stellv. Vorsitzende, Christine Franz: Kassenwartin, Natascha Helbig: Vorsitzende?).
- [ ] Satz „Diese Seite ist noch im Aufbau …“ anpassen oder entfernen.
- [ ] Ggf. Karte „Hier könntest du stehen“ umformulieren (z. B. „Mitglied werden“).

### Texte mit „in Gründung“
- [ ] `src/index.njk` Zeile „Malva ist ein Verein in Gründung. Diese Schritte liegen vor uns:“ → z. B. „So weit sind wir:“
- [ ] `src/impressum.njk` – Hinweis „befindet sich in Gründung“ verschwindet automatisch durch `eingetragen = true` (prüfen).
- [ ] `src/fachleute.njk` – Hinweis „Malva ist ein Verein in Gründung. Deine Anmeldung ist deshalb erst einmal unverbindlich …“ anpassen.
- [ ] Instagram-Vorlagen in `instagram/` – Fußzeile „Malva · Verein in Gründung“ und Captions („Malva ist ein Verein in Gründung …“) für neue Posts anpassen.

### Satzung veröffentlichen
- [ ] Satzung als PDF unter `src/assets/` ablegen und verlinken (z. B. auf „Warum Malva“ oder im Footer) – Transparenz für Spender:innen.

---

## B · Sobald das Vereinskonto besteht

### Spenden – `src/_data/site.js` → `spenden:`
- [ ] `kontoAktiv: false` → **`true`** → Spendenkonto ersetzt oben auf der Spendenseite automatisch den GoFundMe-Kasten.
- [ ] Kontodaten eintragen: `inhaber` („Malva e. V.“), `iban`, `bic`, `bank`, `verwendungszweck`.

### Spendenseite – `src/spenden.njk`
- [ ] QR-Code für Überweisungen (GiroCode/EPC-QR) erstellen und im Konto-Kasten einbauen (kann ich generieren, sobald die IBAN feststeht).
- [ ] Kasten „Spendenkonto mit Spendenquittung – Bald verfügbar“ verschwindet automatisch (prüfen).
- [ ] Entscheiden: GoFundMe weiter anbieten (dann als zweite Möglichkeit unter dem Konto) oder Kampagne beenden?
- [ ] Spendenquittung: wird automatisch erst versprochen, wenn in `site.js` `gemeinnuetzig = true` gesetzt ist (siehe C). Bis dahin steht im Konto-Kasten „Anerkennung läuft noch, keine Spendenquittungen“.

### Außerhalb der Website
- [ ] Instagram-/Facebook-Bio: GoFundMe-Link ggf. durch Website-Link `malva-ev.de/spenden/` ersetzen.
- [ ] Startseite Button „Jetzt unterstützen“ prüfen (führt auf /spenden/ – passt).

---

## C · Nach Anerkennung der Gemeinnützigkeit (Feststellungsbescheid / Freistellungsbescheid)

- [ ] `src/_data/site.js`: `const gemeinnuetzig = false;` → **`true`** (Spendenseite verspricht dann Zuwendungsbestätigungen).
- [ ] Fahrplan: „Anerkennung der Gemeinnützigkeit“ → `"erledigt"`.
- [ ] Impressum oder eigener Abschnitt: „Gemeinnützigkeit: Malva e. V. ist vom Finanzamt [ORT] mit Bescheid vom [DATUM] als gemeinnützig anerkannt. Anerkannte Zwecke: […]“.
- [ ] Spendenseite: Hinweis auf Zuwendungsbestätigung aktivieren („Gib im Verwendungszweck Name und Adresse an … bis 300 € reicht der Kontoauszug“ – Betrag beim Finanzamt prüfen).
- [ ] **Fördermitgliedschaft** – `src/spenden.njk`:
  - [ ] „Bald verfügbar“-Schild, gestrichelten Rahmen und Trenner „Bald verfügbar“ entfernen.
  - [ ] Button „Jetzt vormerken“ → „Jetzt Fördermitglied werden“ mit echtem Weg (Formular, SEPA-Mandat oder Mitgliedsantrag als PDF).
  - [ ] Beiträge mit der beschlossenen **Beitragsordnung** abgleichen (aktuell: 3 € ermäßigt, 5 €, 10 €, 20 €, Wunschbetrag pro Monat).
  - [ ] Satz „steuerlich absetzbar, sobald die Gemeinnützigkeit anerkannt ist“ → „steuerlich absetzbar“.
- [ ] **Förderpartner** – `src/spenden.njk`:
  - [ ] „Bald verfügbar“ entfernen, Button „Jetzt vormerken“ → „Förderpartner werden“.
  - [ ] Stufen mit Beitragsordnung abgleichen (Keimling ab 250 €, Blatt ab 500 €, Blüte ab 1.000 € pro Jahr).
  - [ ] Vorher mit Finanzamt/Steuerberatung klären: Nennung ohne Link = Spende/Duldung, kein Werbesponsoring.
- [ ] **Vorgemerkte Personen** (E-Mails „Vormerkung Fördermitgliedschaft“ / „Vormerkung Förderpartnerschaft“ in info@malva-ev.de) anschreiben.
- [ ] Förderpartner auf `src/partner.njk` unter `foerderpartner:` eintragen (nur Namen, keine Links).
- [ ] Datenschutzerklärung ergänzen: Verarbeitung von Mitglieds- und Bankdaten (SEPA).

---

## D · Wenn Hilfe-Anträge starten

- [ ] `src/hilfe.njk` Abschnitt „Und wenn das alles nicht reicht?“: „Sobald Malva eingetragen … kannst du hier deinen Antrag direkt online stellen“ → Link zum Antrag.
- [ ] FAQ auf `src/hilfe.njk` aktualisieren: „Ab wann kann ich Hilfe beantragen?“, „Wer kann Unterstützung bekommen?“ (Kriterien eintragen), „Was passiert mit meinen Daten?“ (Datenschutzkonzept).
- [ ] Online-Antrag bauen (Plan in `NOTIZEN.md`): Gesundheitsdaten → Formular-Dienst mit EU-Servern, Verschlüsselung, Löschkonzept, eigene Datenschutzhinweise.
- [ ] Startseite „So wollen wir helfen“ und Texte mit „Wir wollen …“ (`src/index.njk`, `src/warum-malva.njk`, `src/hilfe.njk`) in Gegenwart umschreiben: „So helfen wir“, „Wir übernehmen …“.
- [ ] Fahrplan: „Pilotprojekt in Rheinland-Pfalz“ → `"in Arbeit"` bzw. `"erledigt"`.
- [ ] Fachleute-Formular: Formular-Dienst in `site.js` → `formFachleute` eintragen (statt E-Mail), Suche für Hilfesuchende bauen.

---

## Später (2027)
- [ ] Newsletter einbauen: Anbieter mit EU-Servern und Double-Opt-in, Anmeldefeld auf Startseite/Footer, Datenschutzerklärung ergänzen (Plan in `NOTIZEN.md`).

## Immer daran denken
- [ ] Quartalsbericht als PDF auf /aktuelles/ einstellen (siehe README).
- [ ] Nach jeder Änderung bauen (`npm run build`) – die Ausgabe listet noch offene Platzhalter `[ … ]`.
- [ ] Inhalt von `_site/` neu hochladen.
- [ ] Datenschutzerklärung bei jeder neuen Funktion (Formular, Newsletter, Zahlungen) aktualisieren.
- [ ] Stand-Datum auf der Hilfe-Seite (`stand:` oben in `src/hilfe.njk`) aktualisieren.

# Notizen für später

## Idee: Online-Antrag auf „Hilfe beantragen“ (erst nach Eintragung des Vereins)

Vorbild, das Natascha gefallen hat: Antragsseite von nefesch.org (nur als Ideengeber für den Aufbau, keine Texte übernehmen).

**Aufbau der Infoseite**
- Kasten „Was wird finanziert?“ (z. B. Anzahl Sitzungen oder Höchstbetrag pro Person und Jahr, ggf. Eigenanteil) – Malva-Werte noch festlegen
- Kasten „Wichtig zu wissen“ (z. B. welche Fachleute abgerechnet werden können)
- Ablauf in 4 Schritten: 01 Antrag ausfüllen · 02 Fachperson wählen · 03 Absenden · 04 Bestätigung erhalten, Sitzungen wahrnehmen, Rechnung einreichen

**Formular, Abschnitte**
1. Antragsgrundlage nach § 53 AO: seelische Notlage (Nr. 1) / wirtschaftliche Hilfsbedürftigkeit (Nr. 2) / beides – danach nur passende Felder zeigen
2. Persönliche Angaben
3. Schilderung der Situation, optional Attest (ausdrückliche Einwilligung Art. 9 DSGVO), Hinweis auf 112 / 116 117
4. Kontaktdaten (Anschrift, E-Mail, Telefon optional)
5. Kontoverbindung der beratenden Person (Abrechnung direkt Verein ↔ Fachperson)
6. Kontakt zur Fachperson (schon Kontakt? mit wem?)
7. Kosten (Preis pro Sitzung, beantragte Summe)
8. Einverständnisse (Fachperson informiert, Eigenanteil, zweckgebundene Verwendung, Meldepflicht bei Sozialleistungen, Datenschutz), Ort/Datum

**Vor dem Bau unbedingt klären**
- Gesundheitsdaten sind besonders geschützt (Art. 9 DSGVO): Formular braucht verschlüsselte Übertragung, sichere Speicherung (EU-Server), Löschkonzept, Datenschutzerklärung dazu. Die jetzige statische Seite kann keine Formulare verarbeiten, dafür braucht es einen Formular-Dienst oder Server.
- Förderkriterien, Höchstbeträge, Eigenanteil mit Vorstand und Finanzamt abstimmen (Gemeinnützigkeit).
- Regel zu Vermittlungen an Vorstands-/Gründungsmitglieder festlegen (Selbstlosigkeit).

## Sonst offen
- Name und Beschreibung der ersten Partnerin (Link: https://share.google/bUGyNFybHPtcKfPZe)
- Fotos (quadratisch) und Zitate von Jennifer Rebelein und Christine Franz (src/team.njk)

- Datenschutzerklärung (Generator)
- Hosting für die Website (Domainpaket hat keinen Webspace)

## Rechtliche Grundlagen für „Hilfe beantragen“ (zum späteren Einbauen, vorher prüfen lassen)
- **§ 13 Abs. 3 SGB V – Kostenerstattung:** Die Krankenkasse muss die Kosten einer Psychotherapie in einer Privatpraxis erstatten, wenn sie die Behandlung nicht rechtzeitig über Kassenpraxen ermöglichen kann („Systemversagen“). Nur für approbierte Psychotherapeutinnen und Psychotherapeuten, nicht für Heilpraktiker oder Berater. Antrag **vor** Therapiebeginn, Absagen von Kassenpraxen dokumentieren (Datum, Praxis, Grund), bei Ablehnung Widerspruch.
- **§ 75 Abs. 1a SGB V – Terminservicestelle (116 117):** vermittelt Termine für die psychotherapeutische Sprechstunde und Akutbehandlung. Kontakt dort gehört zu den Nachweisen für den Kostenerstattungsantrag.
- **Psychotherapie-Richtlinie (G-BA):** regelt Sprechstunde, Akutbehandlung, probatorische Sitzungen.
- Quellen u. a.: Ratgeber Kostenerstattung der Bundespsychotherapeutenkammer, betanet.de

## Instagram: eigener Post zur Malve (in den nächsten Tagen)
Text-Ideen (Quelle: Apotheken Umschau, AGES):
- „Warum Malva? Die Malve ist eine alte Heilpflanze.“
- Ihre Schleimstoffe legen sich wie ein schützender Film über gereizte Schleimhäute, darum trinkt man Malventee traditionell bei Halsschmerzen und Reizhusten.
- „Sie schützt, sie lindert und sie wächst einfach am Wegesrand. Für alle.“
- Verbindung zu Malva: Hilfe, die schützt und lindert, bis es wieder besser geht, und für alle da ist.
- Text steht auch auf der Website unter „Warum Malva“ → „Warum der Name Malva?“

## Neue Ideen für die Website (Vorbild: Verein aus Karlsruhe, nur als Inspiration, nichts übernehmen)
1. ERLEDIGT (/fachleute/, vorerst per E-Mail; später Formular-Dienst mit EU-Servern in site.js `formFachleute` eintragen) – **Bewerbung für Fachkolleg:innen** (Therapie, Coaching, Beratung, Sozialberatung): Formular mit Beruf/Qualifikation, Schwerpunkten, Ort, vor Ort/online, Kontakt.
2. **Suche für Hilfesuchende**: filterbares Verzeichnis (Berufsgruppe, Schwerpunkt, Ort, online). Plan: Bewerbungen prüft Malva, freigegebene Einträge kommen in eine Datendatei, die Website erzeugt daraus die Liste mit Filtern – ohne Datenbank und Login.
3. **FAQ** unten auf den Seiten (aufklappbare Fragen).
4. **Zahlen auf der Startseite** mit Hochzähl-Animation (z. B. Wartezeit auf Therapieplatz) – nur echte Zahlen mit Quelle (z. B. Bundespsychotherapeutenkammer), Animation abschaltbar für Menschen mit Bewegungsempfindlichkeit.
5. Quartalsbericht ERLEDIGT als Seite /aktuelles/ mit PDF-Download. Newsletter erst 2027 (dann Anbieter mit EU-Servern + Double-Opt-in, z. B. CleverReach, rapidmail, Brevo; Datenschutzerklärung ergänzen) – **Newsletter** und **Quartalsbericht „Aktuelles“**: Newsletter braucht einen DSGVO-konformen Anbieter mit Double-Opt-in (EU/DE), Quartalsbericht als News-Seite auf der Website.
6. **Interner Bereich für Fachkolleg:innen**: vorerst nicht, braucht Login und Server. Später prüfen.
7. **Satzung** als PDF auf der Website veröffentlichen, sobald sie fertig ist (Transparenz).

Reihenfolge laut Natascha: erst Homepage fertig, dann Satzung fürs Finanzamt.

## Reiter „Hilfe“ – ERLEDIGT (Seite /hilfe/), offen: Antragsformular nach Eintragung
- Antrag stellen (eigenes Online-Formular nach Eintragung, siehe oben)
- Sofort-Hilfe: alle Notfall- und Krisennummern gesammelt (112, 116 117, TelefonSeelsorge, 116 016, 116 111 …)
- Selbsthilfegruppen: Verweis auf Selbsthilfe-Kontaktstellen (z. B. NAKOS-Datenbank, regionale Kontaktstellen in RLP)
- Weitere Anlaufstellen: Beratungsstellen, Diakonie, Caritas, Frauenhäuser, Terminservicestelle 116 117
- FAQ

## Satzung – Merkpunkte
- Tätigkeitsgebiet NICHT auf Rheinland-Pfalz beschränken (bundesweit möglich), damit später keine Satzungsänderung nötig ist. Start praktisch in RLP und online.
- Zwecke breit fassen: Therapie, psychologische Beratung, Coaching, Sozialberatung (Anträge Krankenkasse/Ämter), Vermittlung.
- Regel zu Vermittlungen an Vorstands-/Gründungsmitglieder (Selbstlosigkeit).
- Fördermitglieder (ohne Stimmrecht) aufnehmen; Beiträge in eigener Beitragsordnung (privat ab 5 €/Monat, ermäßigt 3 €; Förderpartner Keimling ab 250 €, Blatt ab 500 €, Blüte ab 1.000 €/Jahr). Förderpartner nur namentlich nennen, keine Links (Sponsoring-Steuerfrage mit Finanzamt klären).
- Idee: Kooperation statt Konkurrenz mit anderen Vereinen (z. B. Aktion Therapie, nur Therapie).

## Flyer (DIN lang, 10,5 × 21 cm) – `flyer/`
- Digitale Version fertig (PNG vorne/hinten + PDF) zum Teilen per WhatsApp, ohne QR-Code.
- QR-Codes liegen in `qr-code/` (geprüft, Ziel https://malva-ev.de).
- Druckversion später: QR-Code zur Website in die Lücke über „Kontakt“ auf der Rückseite (Kommentar in `flyer.html`), „(bald online)“ entfernen, ggf. 3 mm Beschnitt je Seite für die Druckerei.

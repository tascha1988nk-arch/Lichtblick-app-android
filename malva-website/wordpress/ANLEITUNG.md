# Malva in WordPress einbauen

## Einmalig
1. **Design → Editor → Stile → (⋮ oben rechts) → Zusätzliches CSS**: Inhalt von `00-zusaetzliches-css.css` komplett einfügen, speichern.
   (Bei älteren Themes: Design → Customizer → Zusätzliches CSS.)
2. **Medien → Datei hinzufügen**: alle Bilder aus `src/assets/img/` hochladen (Dateinamen nicht ändern).
   Die Seiten erwarten sie unter `/wp-content/uploads/2026/11/`. Werden sie in einem anderen Monat hochgeladen, in den Seiten-Codes `2026/11` anpassen.
3. **Einstellungen → Lesen**: „Eine statische Seite“ → Startseite = „Startseite“.
4. **Design → Editor → Website-Icon**: Malva-Bildmarke (`src/favicon.svg` bzw. ein PNG davon).

## Je Seite
1. **Seiten → Erstellen**, Titel wie unten, Titelform (Permalink/Slug) genau so:
   | Datei | Titel | Slug |
   |---|---|---|
   | 01-startseite.html | Startseite | startseite |
   | 02-warum-malva.html | Warum Malva | warum-malva |
   | 03-team.html | Team | team |
   | 04-hilfe.html | Hilfe | hilfe |
   | 05-partner.html | Partner | partner |
   | 06-fachleute.html | Für Fachleute | fachleute |
   | 07-spenden.html | Spenden | spenden |
   | 08-aktuelles.html | Aktuelles | aktuelles |
   | 09-impressum.html | Impressum | impressum |
   | 10-datenschutz.html | Datenschutz | datenschutz |
2. Block **„Individuelles HTML“** einfügen, Inhalt der Datei komplett hineinkopieren.
3. Veröffentlichen.

Kopf, Menü und Fußzeile sind in jedem Code enthalten; der Theme-Kopf und -Fuß werden durch das CSS ausgeblendet.

## Nach Änderungen an der Seite
`npm run build` und dann `python3 wordpress/erzeugen.py` – erzeugt alle Codes neu.

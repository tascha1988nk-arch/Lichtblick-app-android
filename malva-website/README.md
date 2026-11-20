# Malva – Website

Statische Website für Malva (Verein in Gründung), gebaut mit [Eleventy](https://www.11ty.dev/).
Keine Cookies, kein Tracking, Schrift lokal eingebunden.

## Seiten

| Seite | Datei |
|---|---|
| Startseite (inkl. Instagram/Facebook, Fahrplan, Kontakt) | `src/index.njk` |
| Warum Malva (Idee, Geschichte der Gründerin) | `src/warum-malva.njk` |
| Team (Gründungsmitglieder, Karussell) | `src/team.njk` |
| Hilfe (Notfallnummern, Wege zur Therapie, Wartezeit, FAQ) | `src/hilfe.njk` |
| Partner | `src/partner.njk` |
| Spenden (GoFundMe, später Spendenkonto) | `src/spenden.njk` |
| Impressum / Datenschutz | `src/impressum.njk`, `src/datenschutz.njk` |

## Was ändere ich wo?

- **Fast alles Wichtige** steht in `src/_data/site.js`: E-Mail, Social-Links, GoFundMe-Link, Spendenkonto, Fahrplan-Status, Impressumsdaten.
- **Nach der Eintragung ins Vereinsregister:** in `site.js` `eingetragen = true` setzen. Dann steht überall „Malva e. V.“, und das Impressum zeigt Vorstand und Register.
- **Was nach Eintragung, Kontoeröffnung und Gemeinnützigkeit zu ändern ist:** siehe `CHECKLISTE-NACH-EINTRAGUNG.md`.
- **Spendenkonto statt GoFundMe:** in `site.js` `kontoAktiv: true` setzen und IBAN usw. eintragen.
- **Gründungsmitglieder / Partner / Förderpartner:** oben in `src/team.njk` bzw. `src/partner.njk` (Liste im Kopf der Datei). Förderpartner werden nur mit Namen genannt, ohne Link.
- **Beiträge Fördermitglieder / Förderpartner:** oben in `src/spenden.njk`.
- **Meilensteine:** neuen Eintrag oben in `src/meilensteine.njk` unter `geschafft` ergänzen (älteste oben). Kein fester Rhythmus.
- **Fotos:** als `.webp` nach `src/assets/img/` legen.
- **Farben und Gestaltung:** `src/assets/css/style.css`.

Platzhalter stehen in eckigen Klammern `[ … ]` und sind auf der Seite gestrichelt lila markiert. Beim Bauen listet Eleventy auf, wo noch welche offen sind.

## Lokal ansehen und bauen

```bash
npm install
npm start        # Vorschau unter http://localhost:8080
npm run build    # fertige Website im Ordner _site/
```

## Veröffentlichen (z. B. Strato)

Den **Inhalt** des Ordners `_site/` per SFTP in das Webverzeichnis der Domain malva-ev.de hochladen.

// =====================================================================
//  ZENTRALE EINSTELLUNGEN DER MALVA-WEBSITE
//  Alles, was sich ändern kann, steht hier an EINER Stelle.
//  Werte in eckigen Klammern [ … ] sind Platzhalter und werden auf der
//  Website gestrichelt markiert. Vor dem Livegang bitte alle ersetzen.
// =====================================================================

// Nach der Eintragung ins Vereinsregister einfach auf true setzen:
// Dann steht überall „Malva e. V.“ statt „Malva (i. Gr.)“.
const eingetragen = false;

// Erst nach dem Bescheid des Finanzamts auf true setzen: Dann verspricht die
// Spendenseite Zuwendungsbestätigungen (Spendenquittungen).
const gemeinnuetzig = false;

export default {
  eingetragen,
  gemeinnuetzig,
  name: eingetragen ? "Malva e. V." : "Malva (i. Gr.)",
  nameLang: eingetragen ? "Malva e. V." : "Malva – Verein in Gründung",
  slogan: "Weil Hilfe kein Luxus sein sollte.",
  leitsatz: "Psychische Gesundheit darf keine Frage des Geldes sein.",
  url: "https://malva-ev.de",
  ort: "Bad Dürkheim, Rheinland-Pfalz",

  email: "info@malva-ev.de",

  social: {
    instagram: { handle: "@malva.ev", url: "https://www.instagram.com/malva.ev/" },
    facebook: { handle: "Malva", url: "https://www.facebook.com/share/1epeksnani/" },
  },

  // Bewerbungsformular für Fachkolleg:innen: Adresse eines Formular-Dienstes (EU-Server).
  // Leer lassen = das Formular erstellt eine E-Mail an info@malva-ev.de.
  formFachleute: "",

  // Shortcode des Kontaktformulars aus WordPress (WPForms → Alle Formulare → Shortcode)
  formKontakt: '[wpforms id="47" title="false"]',

  // Externes Werkzeug für den Kostenerstattungsantrag (nur Link, nicht eingebunden)
  kever: "https://derkever.de/",

  spenden: {
    // Aktuell: GoFundMe. Später: Spendenkonto (siehe unten).
    gofundme: "https://gofund.me/ce77e6c6f",
    // Sobald es das Vereinskonto gibt, auf true setzen und Daten eintragen.
    kontoAktiv: false,
    konto: {
      inhaber: "Malva e. V.",
      iban: "[IBAN]",
      bic: "[BIC]",
      bank: "[BANK]",
      verwendungszweck: "Spende Malva",
    },
  },

  // Fahrplan auf der Startseite. Status: "erledigt", "in Arbeit" oder "geplant".
  fahrplan: [
    { schritt: "Satzung erarbeiten", status: "geplant" },
    { schritt: "Gründungsversammlung", status: "geplant" },
    { schritt: "Eintragung ins Vereinsregister", status: "geplant" },
    { schritt: "Anerkennung der Gemeinnützigkeit", status: "geplant" },
    { schritt: "Pilotprojekt in Rheinland-Pfalz", status: "geplant" },
  ],

  impressum: {
    name: "Natascha Helbig",
    anschrift: "Kurbrunnenstraße 11, 67098 Bad Dürkheim",
    // Erst nach Eintragung ausfüllen und eingetragen = true setzen:
    vorstand: "[VERTRETUNGSBERECHTIGTER VORSTAND]",
    registergericht: "[REGISTERGERICHT]",
    registernummer: "[REGISTERNUMMER]",
  },

  nav: [
    { url: "/", label: "Start" },
    { url: "/warum-malva/", label: "Warum Malva" },
    { url: "/team/", label: "Team" },
    { url: "/hilfe/", label: "Hilfe finden" },
    { url: "/antrag-stellen/", label: "Antrag stellen" },
    { url: "/partner/", label: "Partner" },
    { url: "/fachleute/", label: "Für Fachleute" },
    { url: "/spenden/", label: "Spenden", button: true },
  ],
};

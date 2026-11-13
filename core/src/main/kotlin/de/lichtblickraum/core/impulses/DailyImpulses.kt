package de.lichtblickraum.core.impulses

import de.lichtblickraum.core.impulses.ImpulseCadence.DAILY
import de.lichtblickraum.core.impulses.ImpulseTheme.BOUNDARIES
import de.lichtblickraum.core.impulses.ImpulseTheme.NEEDS
import de.lichtblickraum.core.impulses.ImpulseTheme.PERCEPTION
import de.lichtblickraum.core.impulses.ImpulseTheme.SELF_COMPASSION
import de.lichtblickraum.core.impulses.ImpulseTheme.SELF_RELATIONSHIP
import de.lichtblickraum.core.impulses.ImpulseTheme.SELF_WORTH

/**
 * Tägliche Impulse (ca. 1–5 Minuten), derzeit 60. Neue Impulse einfach unten anhängen.
 * Die ID muss eindeutig bleiben und sollte nach Veröffentlichung nicht mehr geändert werden.
 */
object DailyImpulses {

    val all: List<Impulse> = listOf(
        Impulse(
            "t01", DAILY, SELF_WORTH, "Etwas, das du an dir magst",
            "Schreibe eine Sache auf, die du an dir magst. Sie darf ganz unabhängig von deinem Aussehen oder deiner Leistung sein.",
            "Wir übersehen leicht, was uns als Mensch ausmacht. Es darf etwas Kleines sein – etwa, dass du gut zuhören kannst.",
            textPrompt = "Was magst du an dir?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t02", DAILY, SELF_COMPASSION, "Ein freundlicher Satz",
            "Sag dir heute einen freundlichen Satz. Wenn ein Kompliment gerade nicht glaubwürdig klingt, reicht: „Ich darf heute freundlich mit mir umgehen.“",
            "Freundliche Worte müssen nicht überschwänglich sein. Ein Satz, der sich ehrlich anfühlt, ist oft wirksamer als ein großes Lob.",
            textPrompt = "Dein Satz für heute (freiwillig)", durationText = "1 Minute",
        ),
        Impulse(
            "t03", DAILY, SELF_COMPASSION, "Gut für dich gesorgt",
            "Notiere etwas, das du heute gut für dich gemacht hast.",
            "Selbstfürsorge zeigt sich oft in kleinen Dingen: ein Glas Wasser, eine Pause, ein Nein. Es lohnt sich, sie zu bemerken.",
            textPrompt = "Was hast du heute für dich getan?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t04", DAILY, NEEDS, "Was brauche ich gerade?",
            "Frage dich: „Was brauche ich gerade?“ Du musst das Bedürfnis nicht sofort erfüllen können.",
            "Ein Bedürfnis wahrzunehmen ist schon ein wertvoller Schritt – auch wenn gerade kein Raum ist, es zu erfüllen.",
            textPrompt = "Was brauchst du gerade?", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t05", DAILY, PERCEPTION, "Etwas Angenehmes bemerken",
            "Nimm dir einen Moment und bemerke etwas Angenehmes um dich herum – ein Geräusch, ein Licht, eine Farbe, eine Temperatur.",
            "Unsere Aufmerksamkeit richtet sich oft auf Probleme. Ein kurzer Blick auf etwas Angenehmes kann ein kleines Gegengewicht sein.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t06", DAILY, SELF_WORTH, "Eine unsichtbare Anstrengung",
            "Erkenne eine kleine Anstrengung an, auch wenn niemand anderes sie gesehen hat.",
            "Vieles, was wir leisten, bleibt unsichtbar – zum Beispiel, wenn wir uns zu etwas aufraffen. Das darf trotzdem zählen.",
            textPrompt = "Welche Anstrengung möchtest du anerkennen?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t07", DAILY, NEEDS, "Eine echte Pause",
            "Gönn dir heute eine kurze Pause, in der du nichts erledigen musst – auch wenn es nur zwei Minuten sind.",
            "Pausen sind kein Luxus, den man sich erst verdienen muss. Schon kurze Unterbrechungen dürfen sein.",
            durationText = "2–5 Minuten",
        ),
        Impulse(
            "t08", DAILY, PERCEPTION, "Kurz in den Körper spüren",
            "Halte für einen Moment inne und frage dich: Wie geht es meinem Körper gerade? Vielleicht magst du die Schultern ein wenig sinken lassen.",
            "Der Körper gibt oft früh Hinweise darauf, wie es uns geht. Es geht nur ums Bemerken, nicht ums Verändern.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t09", DAILY, BOUNDARIES, "Ein kleines Nein",
            "Beobachte heute, ob es einen Moment gibt, in dem du etwas nicht möchtest. Du musst nichts ändern – nur wahrnehmen.",
            "Grenzen fangen mit dem Spüren an. Das Aussprechen darf später kommen, wenn es sich stimmig anfühlt.",
            textPrompt = "Was hast du bemerkt? (freiwillig)", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t10", DAILY, SELF_COMPASSION, "Wie mit einem guten Freund",
            "Wenn heute etwas nicht klappt: Wie würdest du mit einem lieben Menschen sprechen, dem das passiert ist? Versuch es einmal mit dir im selben Ton.",
            "Mit anderen sind viele Menschen geduldiger als mit sich selbst. Dieser Perspektivwechsel kann den inneren Ton etwas weicher machen.",
            durationText = "1–3 Minuten",
        ),
        Impulse(
            "t11", DAILY, SELF_WORTH, "Danke an dich",
            "Bedanke dich innerlich bei dir für etwas, das du heute getan hast – ganz egal, wie klein es ist.",
            "Dankbarkeit richten wir meist an andere. Es darf auch einmal dir selbst gelten.",
            textPrompt = "Wofür möchtest du dir danken?", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t12", DAILY, NEEDS, "Etwas trinken, etwas essen",
            "Prüfe kurz: Hast du heute schon genug getrunken und gegessen? Vielleicht magst du dir jetzt etwas gönnen.",
            "Grundbedürfnisse geraten in vollen Tagen leicht aus dem Blick. Auf sie zu achten ist eine einfache Form von Fürsorge.",
            durationText = "1–5 Minuten",
        ),
        Impulse(
            "t13", DAILY, PERCEPTION, "Ein Moment Himmel",
            "Schau heute einmal bewusst aus dem Fenster oder nach oben in den Himmel. Was siehst du?",
            "Ein kurzer Blick in die Weite kann eine kleine Unterbrechung im Alltag sein.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t14", DAILY, SELF_COMPASSION, "Das darf schwer sein",
            "Wenn heute etwas schwer ist, sag dir: „Das ist gerade schwer – und es ist verständlich, dass es mich beschäftigt.“",
            "Schwieriges anzuerkennen, statt es wegzuschieben, kann entlasten. Es muss nicht gleich gelöst werden.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t15", DAILY, SELF_WORTH, "Eine Stärke im Alltag",
            "Bemerke eine Eigenschaft, die dir heute geholfen hat – zum Beispiel Geduld, Humor oder Neugier.",
            "Stärken zeigen sich oft in gewöhnlichen Momenten. Sie zu benennen, macht sie sichtbarer.",
            textPrompt = "Welche Eigenschaft war heute hilfreich?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t16", DAILY, BOUNDARIES, "Kurz überlegen dürfen",
            "Wenn dich heute jemand um etwas bittet, nimm dir die Freiheit, kurz zu überlegen, bevor du antwortest. Zum Beispiel: „Ich sage dir gleich Bescheid.“",
            "Ein kleiner Moment Bedenkzeit hilft, zu spüren, was du wirklich möchtest.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t17", DAILY, NEEDS, "Was tut mir heute gut?",
            "Überlege, was dir heute guttun könnte – und ob ein kleiner Teil davon möglich ist.",
            "Oft muss es nicht das Ganze sein. Schon ein kleiner Schritt in Richtung dessen, was dir guttut, zählt.",
            textPrompt = "Was könnte dir heute guttun?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t18", DAILY, PERCEPTION, "Ein Geschmack, ein Duft",
            "Nimm beim nächsten Getränk oder Essen den ersten Schluck oder Bissen ganz bewusst wahr.",
            "Bewusstes Wahrnehmen holt uns für einen Augenblick aus dem Gedankenkarussell.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t19", DAILY, SELF_COMPASSION, "Unperfekt ist in Ordnung",
            "Erlaube dir heute, eine Sache nur „gut genug“ statt perfekt zu machen.",
            "Hohe Ansprüche können anstrengend sein. „Gut genug“ ist oft genau richtig.",
            textPrompt = "Was darf heute gut genug sein? (freiwillig)", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t20", DAILY, SELF_WORTH, "Ein gelungener Moment",
            "Erinnere dich an einen Moment von heute oder gestern, der dir gelungen ist – auch wenn er klein war.",
            "Gelungenes geht im Alltag schnell unter. Es bewusst zu bemerken, kann dir einen freundlicheren Blick auf dich schenken.",
            textPrompt = "Was ist dir gelungen?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t21", DAILY, NEEDS, "Ein Gefühl benennen",
            "Frage dich: Welches Gefühl ist gerade da? Ein Wort genügt – zum Beispiel müde, ruhig, angespannt oder froh.",
            "Gefühle zu benennen kann helfen, sie besser zu verstehen. Jedes Gefühl darf da sein.",
            textPrompt = "Dein Wort für dieses Gefühl", durationText = "1 Minute",
        ),
        Impulse(
            "t22", DAILY, BOUNDARIES, "Etwas weglassen",
            "Gibt es heute eine Kleinigkeit auf deiner Liste, die du weglassen oder verschieben darfst?",
            "Nicht alles muss sofort erledigt werden. Etwas bewusst wegzulassen, schafft manchmal ein wenig Luft.",
            textPrompt = "Was darf heute warten? (freiwillig)", durationText = "1–2 Minuten",
        ),
        // ── Ergänzung auf 60 Impulse ──────────────────────────────────────────────
        Impulse(
            "t23", DAILY, SELF_RELATIONSHIP, "Wie geht es dir wirklich?",
            "Frag dich heute einmal so, wie du einen lieben Menschen fragen würdest: „Wie geht es dir wirklich?“ Hör dir die Antwort an, ohne sie gleich zu bewerten.",
            "Andere fragen wir oft aufmerksamer als uns selbst. Diese Frage ist eine kleine Geste der Zuwendung an dich.",
            textPrompt = "Deine ehrliche Antwort (freiwillig)", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t24", DAILY, SELF_WORTH, "Mehr als deine To-do-Liste",
            "Erinnere dich: Du bist mehr als das, was du heute schaffst. Nenne eine Sache an dir, die nichts mit Leistung zu tun hat.",
            "Ein Selbstwert, der nur an Leistung hängt, schwankt mit jedem Tag. Was dich als Mensch ausmacht, bleibt.",
            textPrompt = "Was macht dich aus – jenseits von Leistung?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t25", DAILY, BOUNDARIES, "Wenn der Körper Stopp sagt",
            "Achte heute auf ein kleines Körpersignal, das „Das ist mir zu viel“ bedeuten könnte – etwa Anspannung im Nacken oder ein flaues Gefühl im Bauch.",
            "Der Körper meldet Grenzen oft, bevor der Kopf sie in Worte fasst. Es reicht, das Signal zu bemerken.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t26", DAILY, NEEDS, "Ein Bedürfnis, kein Luxus",
            "Wähle ein Bedürfnis, das du oft hintanstellst – zum Beispiel Ruhe, Bewegung, Gesellschaft oder Zeit allein. Wie könnte ein ganz kleiner Schritt dorthin aussehen?",
            "Bedürfnisse sind keine Extras, die man sich verdienen muss. Ein kleiner Schritt ist genug.",
            textPrompt = "Dein Bedürfnis und ein kleiner Schritt", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t27", DAILY, SELF_COMPASSION, "Eine Hand, die hält",
            "Lege, wenn es sich stimmig anfühlt, für einen Moment eine Hand auf dein Herz oder deinen Bauch. Spüre die Wärme und atme ein paar Mal ruhig.",
            "Eine freundliche Berührung kann ein Gefühl von Halt geben. Wenn sie sich gerade nicht gut anfühlt, lass es einfach.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t28", DAILY, SELF_RELATIONSHIP, "Ein kleines Versprechen",
            "Was möchtest du dir heute gönnen oder ersparen? Formuliere ein kleines, machbares Versprechen an dich.",
            "Kleine Versprechen, die wir uns selbst halten, stärken das Vertrauen in uns.",
            textPrompt = "Dein Versprechen für heute", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t29", DAILY, SELF_WORTH, "Ein Kompliment annehmen",
            "Wenn dir heute jemand etwas Nettes sagt, versuch, es einfach mit „Danke“ anzunehmen – ohne es kleinzureden.",
            "Viele Menschen wehren Lob ganz automatisch ab. Es anzunehmen, darf in kleinen Schritten geübt werden.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t30", DAILY, BOUNDARIES, "Ein freundliches Nein",
            "Denk an eine kleine Bitte, zu der du eigentlich Nein sagen möchtest. Wie könnte ein Nein klingen, das freundlich und klar ist? Du musst es nicht aussprechen.",
            "Ein Nein zu anderen ist oft ein Ja zu dir. Es in Gedanken zu formulieren, macht es vertrauter.",
            textPrompt = "Dein freundliches Nein", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t31", DAILY, NEEDS, "Müde sein dürfen",
            "Wenn du heute müde bist, nimm das ernst. Was wäre eine kleine Möglichkeit, dir etwas Erholung zu schenken?",
            "Müdigkeit ist ein Signal, kein Versagen. Sie zeigt, dass du etwas brauchst.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t32", DAILY, PERCEPTION, "Drei Atemzüge",
            "Nimm drei bewusste Atemzüge. Bemerke, wie die Luft ein- und wieder ausströmt. Mehr ist nicht nötig.",
            "Ein paar bewusste Atemzüge sind eine kleine Pause, die überall möglich ist.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t33", DAILY, SELF_COMPASSION, "Du bist damit nicht allein",
            "Wenn dir heute etwas schwerfällt, erinnere dich: Viele Menschen kennen dieses Gefühl. Du bist damit nicht allein.",
            "Zu wissen, dass andere Ähnliches erleben, kann Scham und Einsamkeit ein wenig leichter machen.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t34", DAILY, SELF_RELATIONSHIP, "Dein inneres Wetter",
            "Wenn deine Stimmung heute ein Wetter wäre – wie sähe es aus? Sonnig, wolkig, stürmisch, neblig? Jedes Wetter darf sein.",
            "Ein Bild für die eigene Stimmung hilft, sie mit etwas Abstand und Freundlichkeit wahrzunehmen.",
            textPrompt = "Dein inneres Wetter", durationText = "1 Minute",
        ),
        Impulse(
            "t35", DAILY, SELF_WORTH, "Ein bisschen stolz",
            "Erinnere dich an etwas aus deinem Leben, worauf du ein bisschen stolz bist. Es darf klein sein oder schon lange zurückliegen.",
            "Stolz darf leise sein. Er erinnert dich daran, was du schon getragen und geschafft hast.",
            textPrompt = "Worauf bist du stolz?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t36", DAILY, BOUNDARIES, "Zeit für dich schützen",
            "Gibt es heute einen kleinen Zeitraum, den du nur für dich reservieren kannst – und sei es eine Viertelstunde?",
            "Auch Zeit für dich ist ein Termin, der zählen darf.",
            textPrompt = "Wann gehört die Zeit dir? (freiwillig)", durationText = "1 Minute",
        ),
        Impulse(
            "t37", DAILY, NEEDS, "Bewegung, die guttut",
            "Gönn deinem Körper heute eine kleine Bewegung, die sich angenehm anfühlt – strecken, die Schultern kreisen lassen, ein paar Schritte gehen.",
            "Es geht nicht um Sport oder Leistung, sondern darum, dem Körper etwas Gutes zu tun.",
            durationText = "1–5 Minuten",
        ),
        Impulse(
            "t38", DAILY, PERCEPTION, "Ein Lieblingsgeräusch",
            "Achte heute auf ein Geräusch, das du magst – vielleicht Vogelstimmen, Regen, Lachen oder Musik.",
            "Angenehmes bewusst wahrzunehmen, schenkt dem Tag kleine Lichtpunkte.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t39", DAILY, SELF_COMPASSION, "Ein Fehler darf ein Fehler sein",
            "Wenn dir heute ein Fehler passiert, versuch einmal zu sagen: „Das ist passiert. Ich bin trotzdem in Ordnung.“",
            "Fehler gehören zum Menschsein und zum Lernen. Sie sagen nichts über deinen Wert aus.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t40", DAILY, SELF_RELATIONSHIP, "Ein freundlicher Gruß an dich",
            "Sag dir heute beim Aufwachen oder vor dem Einschlafen innerlich „Guten Morgen“ oder „Gute Nacht“ – so freundlich, wie du es zu einem lieben Menschen sagen würdest.",
            "Wie wir uns selbst begegnen, prägt unsere Beziehung zu uns. Ein freundlicher Gruß ist ein kleiner Anfang.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t41", DAILY, SELF_WORTH, "Was dir wichtig ist",
            "Was ist dir im Leben wichtig – zum Beispiel Ehrlichkeit, Verbundenheit oder Freiheit? Wähle einen Wert und überlege, wo er heute spürbar war.",
            "Deine Werte zeigen, wer du bist. Sie können dir Orientierung geben, gerade an unruhigen Tagen.",
            textPrompt = "Dein Wert und wo er heute spürbar war", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t42", DAILY, BOUNDARIES, "Nicht sofort antworten müssen",
            "Erlaube dir heute, eine Nachricht nicht sofort zu beantworten, wenn du gerade keine Kraft oder Zeit dafür hast.",
            "Ständige Erreichbarkeit kostet Kraft. Es ist in Ordnung, dir Zeit zu lassen.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t43", DAILY, NEEDS, "Für dich oder mit anderen?",
            "Spür nach: Brauchst du heute eher Ruhe für dich oder eher Kontakt zu anderen Menschen?",
            "Beides sind wichtige Bedürfnisse, und welches gerade größer ist, darf sich von Tag zu Tag ändern.",
            textPrompt = "Was spürst du? (freiwillig)", durationText = "1 Minute",
        ),
        Impulse(
            "t44", DAILY, PERCEPTION, "Etwas Schönes festhalten",
            "Halte heute etwas fest, das dir gefällt – mit der Kamera oder einfach mit einem bewussten, längeren Blick.",
            "Schönes bewusst wahrzunehmen, verankert es ein wenig tiefer im Gedächtnis.",
            textPrompt = "Was hast du entdeckt? (freiwillig)", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t45", DAILY, SELF_COMPASSION, "Eine Pause vom inneren Antreiber",
            "Wenn du merkst, dass du dich antreibst („Ich muss noch …“), halte kurz inne und frag dich: „Muss ich wirklich – oder darf es warten?“",
            "Der innere Antreiber meint es oft gut, ist aber selten sanft. Ein kurzer Moment des Innehaltens schafft Wahlfreiheit.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t46", DAILY, SELF_RELATIONSHIP, "Ein Satz für schwere Tage",
            "Überlege dir einen Satz, der dir an schweren Tagen guttun könnte. Zum Beispiel: „Ich gebe, was ich gerade geben kann – und das reicht.“",
            "Einen solchen Satz bereitzuhalten, kann in schwierigen Momenten wie ein kleiner Anker sein.",
            textPrompt = "Dein Satz für schwere Tage", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t47", DAILY, SELF_WORTH, "Etwas, das du gelernt hast",
            "Denk an etwas, das du heute kannst und früher nicht konntest – praktisch oder im Umgang mit dir und anderen.",
            "Wachstum geschieht oft leise. Es lohnt sich, es ab und zu bewusst zu bemerken.",
            textPrompt = "Was hast du gelernt?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t48", DAILY, BOUNDARIES, "Leichter oder schwerer?",
            "Achte heute darauf, nach welcher Begegnung oder Aufgabe du dich leichter oder schwerer fühlst.",
            "Solche Beobachtungen helfen dir, deine Grenzen und Kraftquellen besser kennenzulernen.",
            textPrompt = "Was ist dir aufgefallen? (freiwillig)", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t49", DAILY, NEEDS, "Etwas Wärmendes",
            "Gönn dir heute etwas Wärmendes: eine warme Tasse, eine weiche Decke oder Sonne auf der Haut.",
            "Wärme ist ein einfaches, körperliches Zeichen von Fürsorge.",
            durationText = "1–5 Minuten",
        ),
        Impulse(
            "t50", DAILY, PERCEPTION, "Ein paar langsame Schritte",
            "Geh ein paar Schritte bewusst langsamer als sonst und spüre, wie deine Füße den Boden berühren.",
            "Langsamkeit kann ein kleines Gegengewicht zu einem eiligen Tag sein.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t51", DAILY, SELF_COMPASSION, "Ein Gefühl darf da sein",
            "Wenn heute ein unangenehmes Gefühl auftaucht, versuch innerlich zu sagen: „Du darfst da sein.“ Du musst es nicht mögen.",
            "Gefühle, die da sein dürfen, werden oft etwas weicher, als wenn wir gegen sie ankämpfen.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t52", DAILY, SELF_RELATIONSHIP, "Dir selbst zuhören",
            "Nimm dir zwei Minuten und schreib einfach auf, was dir gerade durch den Kopf geht – ohne Ordnung und ohne Bewertung.",
            "Gedanken aufzuschreiben, schafft manchmal Platz im Kopf und zeigt, was dich gerade bewegt.",
            textPrompt = "Was dir gerade durch den Kopf geht", durationText = "2–5 Minuten",
        ),
        Impulse(
            "t53", DAILY, SELF_WORTH, "Freundlicher über dich sprechen",
            "Achte heute darauf, wie du über dich sprichst – laut oder innerlich. Vielleicht magst du eine harte Formulierung durch eine mildere ersetzen.",
            "Statt „Ich bin so dumm“ vielleicht: „Das war gerade schwierig für mich.“ Worte prägen, wie wir uns sehen.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t54", DAILY, BOUNDARIES, "Du darfst dich umentscheiden",
            "Gibt es etwas, das du zugesagt hast und das sich inzwischen nicht mehr stimmig anfühlt? Spür nur nach – du musst nichts entscheiden.",
            "Es ist erlaubt, eine Entscheidung neu zu überdenken. Das gehört zu einem achtsamen Umgang mit dir.",
            textPrompt = "Was möchtest du überdenken? (freiwillig)", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t55", DAILY, NEEDS, "Ein kleiner Genuss",
            "Gönn dir heute ganz bewusst einen kleinen Genuss – ohne ihn dir vorher verdienen zu müssen.",
            "Genuss braucht keine Rechtfertigung. Er darf einfach Teil deines Tages sein.",
            textPrompt = "Was hast du genossen? (freiwillig)", durationText = "1–5 Minuten",
        ),
        Impulse(
            "t56", DAILY, PERCEPTION, "Ein Stück Natur",
            "Achte heute auf ein Stück Natur: einen Baum, eine Pflanze, den Wind oder das Licht. Was fällt dir daran auf?",
            "Natur bewusst wahrzunehmen, kann beruhigen und den Blick weiten – auch in der Stadt.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t57", DAILY, SELF_COMPASSION, "Beides darf sein",
            "Frag dich am Abend: „Was war heute schwer – und was war heute gut?“ Beides darf nebeneinanderstehen.",
            "Ein Tag ist selten nur gut oder nur schwer. Beides zu sehen, ist ehrlich und freundlich zugleich.",
            textPrompt = "Was war schwer, was war gut?", durationText = "2–5 Minuten",
        ),
        Impulse(
            "t58", DAILY, SELF_RELATIONSHIP, "Eine Verabredung mit dir",
            "Überlege, was du gern einmal allein unternehmen würdest – ein Café, ein Spaziergang, ein Ausflug. Du musst es heute nicht tun.",
            "Zeit allein kann eine schöne Form sein, die Beziehung zu dir selbst zu pflegen.",
            textPrompt = "Deine Idee", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t59", DAILY, SELF_WORTH, "Du darfst Raum einnehmen",
            "Erlaube dir heute einmal bewusst, Raum einzunehmen: deine Meinung sagen, aufrecht sitzen, deinen Platz behalten.",
            "Du musst dich nicht kleiner machen, als du bist. Dein Platz in der Welt darf da sein.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t60", DAILY, BOUNDARIES, "Ein Ja, das sich gut anfühlt",
            "Bemerke heute ein Ja, das du von Herzen gibst. Wie fühlt es sich an – im Unterschied zu einem Ja aus Pflicht?",
            "Wer spürt, wie sich ein echtes Ja anfühlt, erkennt auch ein Nein leichter.",
            textPrompt = "Was hast du bemerkt? (freiwillig)", durationText = "1–2 Minuten",
        ),
    )
}

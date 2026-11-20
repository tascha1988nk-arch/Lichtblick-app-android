p = '/home/user/Lichtblick-app-android/malva-website/infoblatt/infoblatt-warum-jetzt.html'
s = open(p).read()
s = s.replace('.body{padding:10mm 16mm 0;flex:1;display:flex;flex-direction:column;gap:7mm}',
              '.body{padding:8mm 16mm 7mm;flex:1;display:flex;flex-direction:column;gap:5.5mm}')
s = s.replace('h1{font-weight:600;font-size:13.5mm;line-height:1.02;letter-spacing:-.035em;margin-top:12mm;',
              'h1{font-weight:600;font-size:13mm;line-height:1.02;letter-spacing:-.035em;margin-top:10mm;')
s = s.replace('<span>Dieses Blatt teilen</span>',
              '<span>Dieses Blatt teilen</span><span>Brief an die Politik schicken (Seite 2)</span>')
css_letter = '''
/* Seite 2: Brief */
.letter{background:#fff;padding:16mm 20mm 12mm 25mm;color:#1b1b1b;display:block}
.letter .note{position:absolute;top:6mm;left:25mm;right:20mm;font-size:2.6mm;color:var(--m);display:flex;justify-content:space-between;gap:6mm}
.letter .note b{color:var(--lt);font-weight:600}
.lhead{margin-top:4mm;width:85mm}
.field{border-bottom:.25mm solid #9a9a9a;height:6.2mm;margin-bottom:1.2mm}
.lbl{font-size:2.4mm;color:var(--m);margin-bottom:.6mm;display:block}
.to{margin-top:10mm;font-size:3.9mm;line-height:1.45}
.datum{display:flex;justify-content:flex-end;align-items:flex-end;gap:2mm;margin-top:6mm;font-size:3.6mm}
.datum .field{width:62mm;margin:0}
.betreff{font-weight:600;font-size:4mm;margin-top:8mm;line-height:1.35}
.ltext{font-size:3.55mm;line-height:1.5;margin-top:5mm}
.ltext p{margin-bottom:2.8mm}
.ltext ul{margin:0 0 2.8mm 5mm}
.ltext li{margin-bottom:.8mm}
.own{border:.25mm dashed #9a9a9a;border-radius:2mm;height:30mm;margin:1mm 0 3.5mm;position:relative}
.own span{position:absolute;left:3mm;top:-2.2mm;background:#fff;padding:0 1mm;font-size:2.5mm;color:#6a6a6a}
.sign{margin-top:12mm;width:75mm;border-top:.25mm solid #9a9a9a;font-size:2.4mm;color:var(--m);padding-top:1mm}
.lfoot{position:absolute;left:25mm;right:20mm;bottom:8mm;font-size:2.3mm;color:#8a8a8a;display:flex;justify-content:space-between;gap:6mm}
'''
s = s.replace('</style>', css_letter + '</style>', 1)
letter = '''
<div class="page letter">
  <div class="note"><span><b>Zum Ausfüllen:</b> am Computer ausfüllen oder ausdrucken, unterschreiben und per Post schicken. Oder per E-Mail an poststelle@bmg.bund.de</span><span>Vorlage von Malva · malva-ev.de</span></div>
  <div class="lhead">
    <span class="lbl">Absender:in</span>
    <div class="field" id="f-name"></div>
    <div class="field" id="f-strasse"></div>
    <div class="field" id="f-ort"></div>
    <div class="to">Bundesministerium für Gesundheit<br>11055 Berlin</div>
  </div>
  <div class="datum">Ort, Datum: <div class="field" id="f-datum"></div></div>
  <div class="betreff">Psychotherapie muss für alle erreichbar bleiben:<br>Bitte nehmen Sie die Budgetierung ab 2027 zurück</div>
  <div class="ltext">
    <p>Sehr geehrte Damen und Herren,</p>
    <p>ich schreibe Ihnen, weil ich mir Sorgen um die psychotherapeutische Versorgung in Deutschland mache.</p>
    <p>Schon heute warten Erwachsene im Schnitt fast fünf Monate vom Erstgespräch bis zum Beginn einer Therapie, Kinder und Jugendliche rund sieben Monate. Gleichzeitig zeigt laut der aktuellen COPSY-Studie fast jedes vierte Kind Hinweise auf psychische Auffälligkeiten.</p>
    <p>Ab Januar 2027 soll ambulante Psychotherapie für Erwachsene wieder budgetiert werden. Fachverbände warnen, dass Praxen dann weniger neue Patientinnen und Patienten aufnehmen können. Für Menschen mit wenig Geld, die sich keine private Behandlung leisten können, heißt das: noch länger warten oder gar keine Hilfe bekommen.</p>
    <div class="own" id="f-eigen"><span>Mein persönlicher Grund (freiwillig)</span></div>
    <p>Ich bitte Sie deshalb:</p>
    <ul>
      <li>die Budgetierung der ambulanten Psychotherapie zurückzunehmen,</li>
      <li>mehr Kassensitze für Psychotherapie zu schaffen, besonders auf dem Land,</li>
      <li>dafür zu sorgen, dass psychische Gesundheit keine Frage des Geldes ist.</li>
    </ul>
    <p>Über eine Antwort freue ich mich.</p>
    <p>Mit freundlichen Grüßen</p>
  </div>
  <div class="sign" id="f-sign">Unterschrift (bei E-Mail: dein Name)</div>
  <div class="lfoot"><span>Quellen: Bundespsychotherapeutenkammer (Wartezeiten) · COPSY-Studie, UKE Hamburg 11/2026 · DGVT-BV 2026</span><span>Stand November 2026</span></div>
</div>
'''
s = s.replace('</body>', letter + '</body>')
open(p, 'w').write(s)

"""Legt ausfüllbare Felder über Seite 2 des Infoblatts (PyMuPDF)."""
import json, sys
import pymupdf

ROH = '/home/user/shots/infoblatt-roh.pdf'
OUT = '/home/user/Lichtblick-app-android/malva-website/infoblatt/malva-infoblatt-warum-jetzt.pdf'
PT = 0.75  # CSS-Pixel (96 dpi) -> PDF-Punkte
felder = {f['id']: f for f in json.load(open('/home/user/shots/felder.json'))}
namen = {
    'f-name': ('Name', 'Vorname Nachname'),
    'f-strasse': ('Strasse', 'Straße und Hausnummer'),
    'f-ort': ('PLZ_Ort', 'PLZ und Ort'),
    'f-datum': ('Ort_Datum', 'Ort, Datum'),
    'f-sign': ('Unterschrift_Name', 'Name (bei E-Mail)'),
}

doc = pymupdf.open(ROH)
page = doc[1]
for fid, (name, tip) in namen.items():
    f = felder[fid]
    x0, w = f['x'] * PT, f['w'] * PT
    top, h = f['y'] * PT, f['h'] * PT
    if fid == 'f-eigen':
        rect = pymupdf.Rect(x0 + 4, top + 8, x0 + w - 4, top + h - 3)
    elif fid == 'f-sign':
        rect = pymupdf.Rect(x0, top - 18, x0 + w, top - 1)
    else:
        rect = pymupdf.Rect(x0, top + 1, x0 + w, top + h - 1)
    wd = pymupdf.Widget()
    wd.field_type = pymupdf.PDF_WIDGET_TYPE_TEXT
    wd.field_name = name
    wd.field_label = tip
    wd.rect = rect
    wd.text_font = 'Helv'
    wd.text_fontsize = 10 if fid == 'f-eigen' else 11
    wd.text_color = (0.12, 0.24, 0.2)
    wd.border_width = 0
    wd.fill_color = None
    if fid == 'f-eigen':
        wd.field_flags = pymupdf.PDF_TX_FIELD_IS_MULTILINE
    page.add_widget(wd)

doc.set_metadata({'title': 'Malva Infoblatt: Warum Malva gerade jetzt wichtig ist', 'author': 'Malva (i. Gr.)'})
doc.save(OUT, garbage=3, deflate=True)

# Test: ausfüllen und Seite 2 als Bild speichern
if len(sys.argv) > 1:
    t = pymupdf.open(OUT)
    werte = {'Name': 'Maria Muster', 'Strasse': 'Musterweg 1', 'PLZ_Ort': '67098 Bad Dürkheim',
             'Ort_Datum': 'Bad Dürkheim, 21.11.2026',

             'Unterschrift_Name': 'Maria Muster'}
    for wd in t[1].widgets():
        wd.field_value = werte[wd.field_name]
        wd.update()
    t[1].get_pixmap(dpi=70).save(sys.argv[1])
print('ok', [w.field_name for w in pymupdf.open(OUT)[1].widgets()])

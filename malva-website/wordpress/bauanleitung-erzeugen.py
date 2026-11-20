import base64, html, json, os, re
W = '/home/user/Lichtblick-app-android/malva-website/wordpress/'
IMG = '/home/user/Lichtblick-app-android/malva-website/src/assets/img/'
schrift = open(W + '00a-schrift.css').read()
stil = open(W + '00b-stil.css').read()

pages = [
    ('01-startseite', 'Startseite', 'startseite', 'Als Homepage einstellen: Einstellungen → Lesen → „Eine statische Seite“ → Startseite.'),
    ('02-warum-malva', 'Warum Malva', 'warum-malva', ''),
    ('03-team', 'Team', 'team', ''),
    ('04-hilfe', 'Hilfe', 'hilfe', ''),
    ('05-partner', 'Partner', 'partner', ''),
    ('06-fachleute', 'Für Fachleute', 'fachleute', ''),
    ('07-spenden', 'Spenden', 'spenden', ''),
    ('08-aktuelles', 'Aktuelles', 'aktuelles', ''),
    ('09-impressum', 'Impressum', 'impressum', ''),
    ('10-datenschutz', 'Datenschutz', 'datenschutz', ''),
]

def datauri(name):
    return 'data:image/webp;base64,' + base64.b64encode(open(IMG + name, 'rb').read()).decode()

items = []
for f, title, slug, extra in pages:
    code = open(W + f + '.html').read()
    imgs = sorted(set(re.findall(r'/wp-content/uploads/2026/11/([\w\-.]+)', code)))
    prev = code
    for i in imgs:
        prev = prev.replace('/wp-content/uploads/2026/11/' + i, datauri(i))
    # Links in der Vorschau nicht navigieren lassen
    preview_doc = ('<!doctype html><html lang="de"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">'
                   '<style>' + schrift + stil + '</style><style>a{pointer-events:none}</style></head><body>' + prev + '</body></html>')
    items.append(dict(id=f, title=title, slug=slug, extra=extra, code=code, imgs=imgs, preview=preview_doc))

steps_nav = ''.join(f'<button class="tab" data-i="{i+1}">{i+1}. {html.escape(p["title"])}</button>' for i, p in enumerate(items))

def codebox(cid, label, text, hint):
    return f'''<div class="code">
  <div class="code__head"><span class="code__label">{label}</span><span class="code__size">{len(text)//1024 or 1} kB</span>
    <button class="copy" data-target="{cid}">Code kopieren</button></div>
  <p class="code__hint">{hint}</p>
  <textarea id="{cid}" readonly spellcheck="false">{html.escape(text)}</textarea>
</div>'''

sections = [f'''<section class="step" data-i="0">
  <h2>Schritt 0 · Design einrichten <span class="once">nur einmal</span></h2>
  <ol class="how">
    <li>WordPress: <b>Design → Editor → Stile</b>, oben rechts <b>⋮</b> → <b>Zusätzliches CSS</b>.<br><small>Bei manchen Themes: Design → Customizer → Zusätzliches CSS.</small></li>
    <li>Zuerst den Code <b>Schrift</b> kopieren und einfügen.</li>
    <li>Direkt darunter den Code <b>Stil</b> einfügen.</li>
    <li><b>Speichern.</b></li>
  </ol>
  {codebox('c-schrift', '1 · Schrift', schrift, 'Die Schrift „Outfit“ ist direkt eingebaut. So lädt nichts von Google, das ist wichtig für den Datenschutz.')}
  {codebox('c-stil', '2 · Stil', stil, 'Farben, Abstände und Aufbau. Blendet außerdem Kopf und Fuß des WordPress-Themes aus.')}
</section>''']

for i, p in enumerate(items, 1):
    imgs = ''.join(f'<li><code>{x}</code></li>' for x in p['imgs']) or '<li>keine</li>'
    extra = f'<li>{html.escape(p["extra"])}</li>' if p['extra'] else ''
    sections.append(f'''<section class="step" data-i="{i}" hidden>
  <h2>Schritt {i} · {html.escape(p["title"])}</h2>
  <ol class="how">
    <li><b>Seiten → Erstellen</b>, Titel: <b>{html.escape(p["title"])}</b>. Rechts unter „Link“ die Adresse auf <b>{p["slug"]}</b> stellen.</li>
    <li><b>+</b> → Block <b>„Individuelles HTML“</b> → den Code unten komplett einfügen.</li>
    <li>Bilder vorher unter <b>Medien → Datei hinzufügen</b> hochladen (Name nicht ändern):<ul class="imgs">{imgs}</ul></li>
    {extra}
    <li><b>Veröffentlichen.</b></li>
  </ol>
  <div class="prev">
    <div class="prev__bar"><span>Vorschau</span><span class="prev__url">malva-ev.de/{'' if p['slug']=='startseite' else p['slug']+'/'}</span>
      <span class="prev__sw"><button class="vw on" data-w="100%">Computer</button><button class="vw" data-w="390px">Handy</button></span></div>
    <div class="prev__frame"><iframe sandbox="allow-scripts" loading="lazy" title="Vorschau {html.escape(p["title"])}" data-doc="{p['id']}"></iframe></div>
  </div>
  {codebox('c-' + p['id'], 'Code für die Seite', p['code'], 'In einen Block „Individuelles HTML“ einfügen.')}
</section>''')

docs = json.dumps({p['id']: p['preview'] for p in items}).replace('</', '<\\/')

page = f'''<!doctype html>
<html lang="de"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Malva in WordPress</title>
<style>
{schrift}
:root{{--g:#1F3D33;--c:#F4F2EE;--m:#4E5A55;--l:#6A4BC4;--s:#2F6B48;--line:#D9D5CC;--tint:#ECE9F6}}
*{{box-sizing:border-box}}
body{{margin:0;background:var(--c);color:var(--g);font-family:Outfit,system-ui,sans-serif;font-size:16px;line-height:1.55}}
header{{background:var(--g);color:var(--c);padding:18px 16px 0;position:sticky;top:0;z-index:5}}
header h1{{margin:0 0 4px;font-size:22px;font-weight:600;letter-spacing:-.02em}}
header p{{margin:0 0 12px;color:#9CCBA8;font-size:14px}}
.tabs{{display:flex;gap:6px;overflow-x:auto;padding-bottom:12px;scrollbar-width:thin}}
.tab{{flex:none;font:inherit;font-size:14px;font-weight:500;border:1.5px solid rgba(244,242,238,.35);background:transparent;color:var(--c);border-radius:999px;padding:6px 12px;cursor:pointer}}
.tab.on{{background:#C3B1F2;border-color:#C3B1F2;color:var(--g)}}
main{{max-width:1100px;margin:0 auto;padding:20px 16px 60px}}
h2{{font-size:24px;letter-spacing:-.02em;margin:4px 0 14px}}
.once{{font-size:13px;font-weight:600;background:var(--tint);color:var(--l);border-radius:999px;padding:3px 10px;vertical-align:middle;margin-left:6px}}
.how{{background:#fff;border-radius:14px;padding:16px 16px 16px 36px;margin:0 0 18px}}
.how li{{margin:4px 0}} .how small{{color:var(--m)}}
.imgs{{margin:6px 0 0;padding-left:18px}} code{{font-size:13px;background:var(--c);padding:1px 6px;border-radius:5px}}
.prev{{background:#fff;border-radius:14px;overflow:hidden;margin-bottom:18px;border:1px solid var(--line)}}
.prev__bar{{display:flex;align-items:center;gap:10px;flex-wrap:wrap;padding:8px 12px;border-bottom:1px solid var(--line);font-size:14px}}
.prev__bar>span:first-child{{font-weight:600}}
.prev__url{{color:var(--m);background:var(--c);border-radius:999px;padding:2px 10px;font-size:13px}}
.prev__sw{{margin-left:auto;display:flex;gap:4px}}
.vw{{font:inherit;font-size:13px;border:1.5px solid var(--g);background:#fff;color:var(--g);border-radius:999px;padding:3px 10px;cursor:pointer}}
.vw.on{{background:var(--g);color:var(--c)}}
.prev__frame{{background:#d9d6cf;display:flex;justify-content:center}}
iframe{{display:block;width:100%;height:640px;border:0;background:var(--c);transition:width .2s}}
.code{{background:#fff;border-radius:14px;padding:14px;margin-bottom:18px;border:1px solid var(--line)}}
.code__head{{display:flex;align-items:center;gap:10px;flex-wrap:wrap}}
.code__label{{font-weight:600;font-size:17px}}
.code__size{{font-size:12px;color:var(--m)}}
.copy{{margin-left:auto;font:inherit;font-weight:600;font-size:15px;border:0;background:var(--s);color:#fff;border-radius:999px;padding:9px 18px;cursor:pointer}}
.copy.done{{background:#6A4BC4}}
.code__hint{{margin:6px 0 10px;color:var(--m);font-size:14px}}
textarea{{width:100%;height:180px;font:12px/1.45 ui-monospace,Menlo,Consolas,monospace;color:#2b3a34;background:#FAF9F6;border:1px solid var(--line);border-radius:10px;padding:10px;resize:vertical}}
.navbtns{{display:flex;justify-content:space-between;gap:10px;margin-top:8px}}
.navbtns button{{font:inherit;font-weight:600;border:2px solid var(--g);background:transparent;color:var(--g);border-radius:999px;padding:9px 18px;cursor:pointer}}
.navbtns button:disabled{{opacity:.3}}
:focus-visible{{outline:3px solid #6A4BC4;outline-offset:2px}}
</style></head><body>
<header>
  <h1>Malva in WordPress</h1>
  <p>Schritt für Schritt: Vorschau ansehen, Code kopieren, in WordPress einfügen.</p>
  <nav class="tabs" aria-label="Schritte"><button class="tab on" data-i="0">0. Design</button>{steps_nav}</nav>
</header>
<main>
{''.join(sections)}
<div class="navbtns"><button id="prev">← Zurück</button><button id="next">Weiter →</button></div>
</main>
<script>
const DOCS={docs};
let cur=0;const max={len(items)};
function show(i){{cur=i;
  document.querySelectorAll('.step').forEach(s=>s.hidden=+s.dataset.i!==i);
  document.querySelectorAll('.tab').forEach(t=>t.classList.toggle('on',+t.dataset.i===i));
  const on=document.querySelector('.tab.on');if(on)on.scrollIntoView({{inline:'center',block:'nearest'}});
  const f=document.querySelector('.step[data-i="'+i+'"] iframe');if(f&&!f.srcdoc)f.srcdoc=DOCS[f.dataset.doc];
  document.getElementById('prev').disabled=i===0;document.getElementById('next').disabled=i===max;
  window.scrollTo(0,0);}}
document.querySelectorAll('.tab').forEach(t=>t.onclick=()=>show(+t.dataset.i));
document.getElementById('prev').onclick=()=>show(Math.max(0,cur-1));
document.getElementById('next').onclick=()=>show(Math.min(max,cur+1));
document.querySelectorAll('.vw').forEach(b=>b.onclick=()=>{{const box=b.closest('.prev');box.querySelectorAll('.vw').forEach(x=>x.classList.toggle('on',x===b));box.querySelector('iframe').style.width=b.dataset.w;}});
document.querySelectorAll('.copy').forEach(b=>b.onclick=async()=>{{
  const ta=document.getElementById(b.dataset.target);let ok=false;
  try{{await navigator.clipboard.writeText(ta.value);ok=true;}}catch(e){{}}
  if(!ok){{ta.focus();ta.select();try{{ok=document.execCommand('copy');}}catch(e){{}}}}
  b.textContent=ok?'Kopiert ✓':'Markiert – jetzt Strg+C';b.classList.add('done');
  setTimeout(()=>{{b.textContent='Code kopieren';b.classList.remove('done')}},2500);}});
show(0);
</script>
</body></html>'''
open(W + 'bauanleitung.html', 'w').write(page)
print(round(len(page) / 1024), 'kB')

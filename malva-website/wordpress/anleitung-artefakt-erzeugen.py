import base64, html, os
W = '/home/user/Lichtblick-app-android/malva-website/wordpress/'
P = '/home/user/shots/prev/'
OUT = '/tmp/claude-0/-home-user-Lichtblick-app-android/7c7936ef-22cc-5b68-84de-c5a0e99af8ee/scratchpad/malva-wordpress-einbauen.html'
os.makedirs(os.path.dirname(OUT), exist_ok=True)

schrift = open(W + '00a-schrift.css').read()
stil = open(W + '00b-stil.css').read()
font_faces = schrift.split('\n', 1)[1]  # für die Seite selbst

pages = [
    ('01-startseite', 'Startseite', 'startseite', ['natascha-helbig-sw.webp'],
     'Danach: <b>Einstellungen → Lesen</b> → „Eine statische Seite“ → Homepage: <b>Startseite</b>.'),
    ('02-warum-malva', 'Warum Malva', 'warum-malva', None, ''),
    ('03-team', 'Team', 'team', None, ''),
    ('04-hilfe', 'Hilfe', 'hilfe', None, ''),
    ('05-partner', 'Partner', 'partner', None, ''),
    ('06-fachleute', 'Für Fachleute', 'fachleute', None, 'Anmeldungen kommen als E-Mail an info@malva-ev.de. Du prüfst und gibst frei, erst dann kommt jemand in die Suche.'),
    ('07-spenden', 'Spenden', 'spenden', None, ''),
    ('08-meilensteine', 'Meilensteine', 'meilensteine', None, 'Neue Meilensteine schickst du mir einfach, ich schreibe sie in den Code.'),
    ('09-impressum', 'Impressum', 'impressum', None, ''),
    ('10-datenschutz', 'Datenschutz', 'datenschutz', None, 'Die Datenschutzerklärung selbst fehlt noch (Platzhalter).'),
]
import re

def b64(path, mime):
    return f'data:{mime};base64,' + base64.b64encode(open(path, 'rb').read()).decode()

def copybox(cid, label, text, note):
    return f'''<div class="codebox">
  <div class="codebox__head"><h4>{label}</h4><button type="button" class="copy" data-for="{cid}">{label.split(' ')[0] if False else 'Kopieren'}</button></div>
  <p class="note">{note}</p>
  <textarea id="{cid}" readonly spellcheck="false" aria-label="{label}">{html.escape(text)}</textarea>
</div>'''

tabs, panels = [], []
tabs.append('<button type="button" class="tab" data-step="0" aria-selected="true">Vorab</button>')
panels.append(f'''<section class="panel" data-step="0">
  <p class="eyebrow">Wichtig vorab</p>
  <h2>Das Design steckt jetzt in jeder Seite</h2>
  <p class="lead">Schrift und Stil sind direkt im Seiten-Code enthalten. Du musst nichts mehr unter „Zusätzliches CSS“ einfügen. So sieht jede Seite sofort richtig aus.</p>
  <div class="card">
    <span class="num">!</span>
    <div class="card__body">
      <h3>Seiten, die du schon angelegt hast</h3>
      <ol class="how"><li>Seite öffnen, <span class="ui">⋮ → Code-Editor</span>.</li>
      <li>Alles im großen Feld markieren und löschen (Strg+A, Entf).</li>
      <li>Den neuen Seiten-Code aus dem passenden Schritt einfügen.</li>
      <li><span class="ui">Code-Editor verlassen</span>, <span class="ui">Aktualisieren</span>. Die Fotos sind schon im Code.</li></ol>
      <p class="tip">Falls du Schrift oder Stil schon unter „Zusätzliches CSS“ eingefügt hast: Das darfst du drin lassen, es stört nicht.</p>
    </div>
  </div>
  <div class="card">
    <span class="num">?</span>
    <div class="card__body">
      <h3>Seite bleibt schwarz-weiß? Stil-Code als Zusätzliches CSS</h3>
      <ol class="how"><li><span class="ui">Design → Editor → Stile → ⋮ → Zusätzliches CSS</span></li>
      <li>Alles darin löschen, nur diesen Stil-Code einfügen, <span class="ui">Speichern</span>.</li>
      <li>Seite neu laden.</li></ol>
      {copybox('c-stil', 'Stil-Code', stil, 'Ohne Schrift-Code. Die Seite nutzt dann vorerst die Standardschrift.')}
    </div>
  </div>
</section>''')

for n, (pid, title, slug, _, extra) in enumerate(pages, 1):
    code = open(W + pid + '.html').read()
    # Bilder direkt in den Code schreiben (kein Hochladen nötig)
    block = code.strip()
    for name in set(re.findall(r'/wp-content/uploads/2026/11/([\w\-.]+)', block)):
        block = block.replace('/wp-content/uploads/2026/11/' + name, b64('/home/user/Lichtblick-app-android/malva-website/src/assets/img/' + name, 'image/webp'))
    block = '<!-- wp:html -->\n<style>\n' + schrift + stil + '</style>\n' + block + '\n<!-- /wp:html -->\n'
    nimg = 0
    imgs = sorted(set(re.findall(r'/wp-content/uploads/2026/11/([\w\-.]+)', code)))
    pc = b64(f'{P}{pid}-pc.jpg', 'image/jpeg')
    ph = b64(f'{P}{pid}-handy.jpg', 'image/jpeg')
    img_step = ''
    if imgs:
        img_step = '<li>Bilder hochladen: <span class="ui">Medien → Datei hinzufügen</span>, Namen nicht ändern:<ul class="files">' + ''.join(f'<li><code>{i}</code></li>' for i in imgs) + '</ul></li>'
    url = 'malva-ev.de' + ('' if slug == 'startseite' else f'/{slug}/')
    tabs.append(f'<button type="button" class="tab" data-step="{n}" aria-selected="false">{html.escape(title)}</button>')
    panels.append(f'''<section class="panel" data-step="{n}" hidden>
  <p class="eyebrow">Schritt {n} von {len(pages)}</p>
  <h2>{html.escape(title)}</h2>
  <p class="lead">Adresse: <code>{url}</code></p>
  <div class="card">
    <span class="num">1</span>
    <div class="card__body">
      <h3>Neue Seite anlegen</h3>
      <ol class="how">
        <li><span class="ui">Seiten → Neue Seite hinzufügen</span>, Titel: <b>{html.escape(title)}</b></li>
        <li>Rechts unter <span class="ui">Link</span> die Adresse auf <b>{slug}</b> stellen.</li>
        <li>Oben rechts <span class="ui">⋮ → Code-Editor</span>.</li>
        <li>Seiten-Code unten kopieren und ins große Feld unter dem Titel einfügen.</li>
        <li><span class="ui">Code-Editor verlassen</span>.</li>
        {f'<li>Im Editor erscheinen {nimg} leere Bildfelder. Jedes anklicken → <span class="ui">Mediathek</span> → Bild wählen. Welches Bild wohin gehört, siehst du unten in der Vorschau.</li>' if nimg else ''}
        <li><span class="ui">Veröffentlichen</span>.</li>
      </ol>
      {f'<p class="tip">{extra}</p>' if extra else ''}
      {copybox('c-' + pid, 'Seiten-Code', block, 'Die Fotos sind direkt im Code enthalten, du musst nichts hochladen. Deshalb ist der Code etwas länger.')}
    </div>
  </div>
  <div class="card card--preview">
    <span class="num">2</span>
    <div class="card__body">
      <div class="prevhead"><h3>So sieht die Seite aus</h3>
        <div class="seg" role="group" aria-label="Ansicht"><button type="button" class="segbtn on" data-view="pc">Computer</button><button type="button" class="segbtn" data-view="handy">Handy</button></div></div>
      <div class="shot"><img class="shot--pc" src="{pc}" alt="Vorschau {html.escape(title)} am Computer" loading="lazy"><img class="shot--handy" src="{ph}" alt="Vorschau {html.escape(title)} am Handy" loading="lazy" hidden></div>
    </div>
  </div>
</section>''')

page = f'''<title>Malva WordPress-Einbau</title>
<meta name="claude:viewport-fit-sides" content="cover">
<style>
{font_faces}
/* Layout: oben Schritt-Reiter, darunter nummerierte Karten pro Schritt, Code-Kästen mit Kopieren-Button */
:root{{
  --bg:#F4F2EE; --surface:#FFFFFF; --ink:#1F3D33; --muted:#4E5A55; --line:#DDD8CE;
  --accent:#2F6B48; --accent-ink:#FFFFFF; --lilac:#6A4BC4; --chip:#ECE9F6; --code-bg:#FAF8F3; --band:#1F3D33; --band-ink:#F4F2EE; --band-muted:#9CCBA8;
  --display:"Outfit",system-ui,sans-serif; --body:"Outfit",system-ui,sans-serif; --mono:ui-monospace,"SF Mono",Menlo,Consolas,monospace;
}}
@media (prefers-color-scheme: dark){{:root:not([data-theme="light"]){{
  --bg:#14231E; --surface:#1B2E28; --ink:#ECEDE7; --muted:#A9B8B1; --line:#2E4840;
  --accent:#9CCBA8; --accent-ink:#14231E; --lilac:#C3B1F2; --chip:#2B2A45; --code-bg:#11201B; --band:#0F1C18; --band-ink:#ECEDE7; --band-muted:#9CCBA8; color-scheme:dark}}}}
:root[data-theme="dark"]{{
  --bg:#14231E; --surface:#1B2E28; --ink:#ECEDE7; --muted:#A9B8B1; --line:#2E4840;
  --accent:#9CCBA8; --accent-ink:#14231E; --lilac:#C3B1F2; --chip:#2B2A45; --code-bg:#11201B; --band:#0F1C18; --band-ink:#ECEDE7; --band-muted:#9CCBA8; color-scheme:dark}}
*{{box-sizing:border-box}}
[hidden]{{display:none!important}}
body{{background:var(--bg);color:var(--ink);font-family:var(--body);font-size:17px;line-height:1.55}}
body::before{{content:"";position:fixed;inset:0 0 auto;height:env(safe-area-inset-top,0px);z-index:10;background:var(--band)}}
.top{{position:sticky;top:env(safe-area-inset-top,0px);z-index:5;background:var(--band);color:var(--band-ink)}}
.top__in,.wrap{{max-width:980px;margin:0 auto;padding-left:max(16px,env(safe-area-inset-left,0px));padding-right:max(16px,env(safe-area-inset-right,0px))}}
.top__in{{padding-block:14px 12px}}
.brand{{display:flex;align-items:center;gap:10px;margin:0 0 2px}}
.brand svg{{width:30px;height:30px}}
.brand h1{{margin:0;font-family:var(--display);font-weight:600;font-size:21px;letter-spacing:-.02em}}
.top p{{margin:0 0 10px;color:var(--band-muted);font-size:14px}}
.tabs{{display:flex;gap:6px;overflow-x:auto;padding-bottom:2px;scrollbar-width:thin}}
.tab{{flex:none;font:500 14px/1 var(--body);color:var(--band-ink);background:transparent;border:1.5px solid color-mix(in srgb,var(--band-ink) 35%,transparent);border-radius:999px;padding:8px 13px;cursor:pointer}}
.tab .n{{opacity:.7;margin-right:4px}}
.tab[aria-selected="true"]{{background:#C3B1F2;border-color:#C3B1F2;color:#1F3D33}}
.wrap{{padding-block:22px 64px}}
.eyebrow{{margin:0;font-size:13px;font-weight:600;letter-spacing:.1em;text-transform:uppercase;color:var(--lilac)}}
h2{{font-family:var(--display);font-size:clamp(28px,5vw,40px);font-weight:600;letter-spacing:-.025em;line-height:1.1;margin:4px 0 8px;text-wrap:balance}}
.lead{{margin:0 0 20px;color:var(--muted);max-width:62ch}}
.panel{{display:flex;flex-direction:column;gap:16px}}
.card{{display:grid;grid-template-columns:44px minmax(0,1fr);gap:14px;background:var(--surface);border:1px solid var(--line);border-radius:18px;padding:20px}}
.num{{width:40px;height:40px;border-radius:50%;display:grid;place-items:center;background:var(--chip);color:var(--lilac);font-weight:600;font-size:18px}}
.card__body{{min-width:0}}
h3{{font-family:var(--display);font-size:22px;font-weight:600;letter-spacing:-.015em;margin:6px 0 10px}}
h4{{margin:0;font-size:16px;font-weight:600}}
.card p{{margin:0 0 12px}}
.how{{margin:0 0 14px;padding-left:22px;display:flex;flex-direction:column;gap:7px}}
.files{{margin:6px 0 2px;padding-left:18px}}
.ui{{background:var(--chip);color:var(--ink);border-radius:7px;padding:1px 7px;font-weight:500;white-space:nowrap}}
code{{font-family:var(--mono);font-size:.86em;background:var(--code-bg);border:1px solid var(--line);border-radius:6px;padding:1px 6px}}
.tip{{border-left:3px solid var(--lilac);padding:4px 0 4px 12px;color:var(--muted)}}
.codebox{{border:1px solid var(--line);border-radius:14px;padding:14px;background:var(--code-bg);margin-top:6px}}
.codebox__head{{display:flex;align-items:center;justify-content:space-between;gap:12px;flex-wrap:wrap}}
.note{{font-size:14px;color:var(--muted);margin:4px 0 10px!important}}
.copy{{font:600 15px/1 var(--body);background:var(--accent);color:var(--accent-ink);border:0;border-radius:999px;padding:11px 20px;cursor:pointer;min-width:132px}}
.copy.ok{{background:var(--lilac);color:var(--bg)}}
textarea{{display:block;width:100%;height:170px;resize:vertical;font:12.5px/1.5 var(--mono);color:var(--ink);background:var(--surface);border:1px solid var(--line);border-radius:10px;padding:10px;white-space:pre;overflow:auto}}
.prevhead{{display:flex;align-items:center;justify-content:space-between;gap:12px;flex-wrap:wrap;margin-bottom:12px}}
.prevhead h3{{margin:6px 0}}
.seg{{display:flex;border:1.5px solid var(--ink);border-radius:999px;overflow:hidden}}
.segbtn{{font:500 14px/1 var(--body);background:transparent;color:var(--ink);border:0;padding:8px 14px;cursor:pointer}}
.segbtn.on{{background:var(--ink);color:var(--bg)}}
.shot{{max-height:620px;overflow:auto;border:1px solid var(--line);border-radius:12px;background:#F4F2EE;display:flex;justify-content:center}}
.shot img{{display:block;max-width:100%;height:auto}}
.shot--handy{{width:390px}}
.pager{{display:flex;justify-content:space-between;gap:12px;margin-top:22px}}
.pager button{{font:600 15px/1 var(--body);background:transparent;color:var(--ink);border:1.5px solid var(--ink);border-radius:999px;padding:11px 20px;cursor:pointer}}
.pager button:disabled{{opacity:.3;cursor:default}}
:focus-visible{{outline:3px solid var(--lilac);outline-offset:2px}}
@media (max-width:560px){{.card{{grid-template-columns:1fr;padding:16px}} .num{{width:34px;height:34px;font-size:16px}} .ui{{white-space:normal}}}}
@media (prefers-reduced-motion:no-preference){{.copy,.tab,.segbtn{{transition:background .15s,color .15s}}}}
</style>
<header class="top"><div class="top__in">
  <div class="brand"><svg viewBox="0 0 200 200" aria-hidden="true"><path transform="translate(84 104) rotate(-26)" d="M0 -62 C34 -30 34 30 0 62 C-34 30 -34 -30 0 -62 Z" fill="#C3B1F2" fill-opacity=".92"/><path transform="translate(116 104) rotate(26)" d="M0 -62 C34 -30 34 30 0 62 C-34 30 -34 -30 0 -62 Z" fill="#9CCBA8" fill-opacity=".85"/></svg><h1>Malva in WordPress einbauen</h1></div>
  <p>Seite für Seite: Code kopieren, im Code-Editor einfügen, Bilder setzen, veröffentlichen.</p>
  <nav class="tabs" aria-label="Schritte">{''.join(tabs)}</nav>
</div></header>
<main class="wrap">
{''.join(panels)}
<div class="pager"><button type="button" id="back">← Zurück</button><button type="button" id="next">Weiter →</button></div>
</main>
<script>
(function(){{
  var tabs=[].slice.call(document.querySelectorAll('.tab')),panels=[].slice.call(document.querySelectorAll('.panel')),cur=0,last=panels.length-1;
  tabs.forEach(function(t,i){{t.insertAdjacentHTML('afterbegin','<span class="n">'+i+'</span>');t.onclick=function(){{go(i)}}}});
  function go(i){{cur=i;panels.forEach(function(p,k){{p.hidden=k!==i}});tabs.forEach(function(t,k){{t.setAttribute('aria-selected',k===i?'true':'false')}});
    tabs[i].scrollIntoView({{inline:'center',block:'nearest'}});document.getElementById('back').disabled=i===0;document.getElementById('next').disabled=i===last;window.scrollTo(0,0);
    try{{localStorage.setItem('malva-wp-step',i)}}catch(e){{}}}}
  document.getElementById('back').onclick=function(){{go(Math.max(0,cur-1))}};
  document.getElementById('next').onclick=function(){{go(Math.min(last,cur+1))}};
  document.querySelectorAll('.segbtn').forEach(function(b){{b.onclick=function(){{var c=b.closest('.card');c.querySelectorAll('.segbtn').forEach(function(x){{x.classList.toggle('on',x===b)}});
    c.querySelector('.shot--pc').hidden=b.dataset.view!=='pc';c.querySelector('.shot--handy').hidden=b.dataset.view!=='handy';}}}});
  document.querySelectorAll('.copy').forEach(function(b){{b.onclick=function(){{var ta=document.getElementById(b.dataset.for),label='Kopieren';
    function done(t){{b.textContent=t;b.classList.add('ok');setTimeout(function(){{b.textContent=label;b.classList.remove('ok')}},2600)}}
    function fallback(){{ta.focus();ta.select();var ok=false;try{{ok=document.execCommand('copy')}}catch(e){{}}done(ok?'Kopiert ✓':'Markiert, jetzt Strg+C')}}
    try{{navigator.clipboard.writeText(ta.value).then(function(){{done('Kopiert ✓')}},fallback)}}catch(e){{fallback()}}}}}});
  var s=0;try{{s=+localStorage.getItem('malva-wp-step')||0}}catch(e){{}}go(Math.min(Math.max(s,0),last));
}})();
</script>
'''
open(OUT, 'w').write(page)
print(OUT, round(len(page) / 1024), 'kB')

"""Erzeugt WordPress-Bausteine aus der fertigen Malva-Seite:
 - 00-zusaetzliches-css.css  (einmal in WordPress unter "Zusätzliches CSS")
 - <seite>.html              (je Seite in einen Block "Individuelles HTML")
"""
import base64, re, os, sys

ROOT = '/home/user/Lichtblick-app-android/malva-website'
OUT = ROOT + '/wordpress'
UP = '/wp-content/uploads/2026/11/'
os.makedirs(OUT, exist_ok=True)

# ---------- CSS ----------
css = open(ROOT + '/src/assets/css/style.css').read()
css = re.sub(r'/\*.*?\*/', '', css, flags=re.S)
# Schriften: nur latin, als data-URI einbetten (keine externen Dateien nötig)
fonts = ''
for w in (400, 500, 600):
    b = base64.b64encode(open(f'{ROOT}/src/assets/fonts/outfit-latin-{w}-normal.woff2', 'rb').read()).decode()
    fonts += f'@font-face{{font-family:"Outfit";font-style:normal;font-weight:{w};font-display:swap;src:url(data:font/woff2;base64,{b}) format("woff2")}}\n'
css = re.sub(r'@font-face\s*\{[^}]*\}', '', css)

def prefix_sel(sel):
    sel = sel.strip()
    if not sel:
        return sel
    if sel in (':root', 'html', 'body'):
        return '.malva'
    if sel.startswith('.js '):
        return '.malva.js ' + sel[4:]
    if sel.startswith('html '):
        return '.malva ' + sel[5:]
    if sel.startswith('*'):
        return '.malva ' + sel
    return '.malva ' + sel

def process(block):
    out = []
    i = 0
    n = len(block)
    while i < n:
        if block[i].isspace():
            i += 1; continue
        j = block.find('{', i)
        if j < 0:
            break
        head = block[i:j].strip()
        depth = 1; k = j + 1
        while depth and k < n:
            if block[k] == '{': depth += 1
            elif block[k] == '}': depth -= 1
            k += 1
        body = block[j + 1:k - 1]
        if head.startswith('@media') or head.startswith('@supports'):
            out.append(head + '{' + process(body) + '}')
        elif head.startswith('@'):
            out.append(head + '{' + body + '}')
        else:
            sels = ','.join(prefix_sel(s) for s in head.split(','))
            out.append(sels + '{' + body.strip() + '}')
        i = k
    return '\n'.join(out)

wp_fix = '''
/* ===== Anpassung an WordPress: Theme-Kopf/Fuß und Abstände ausblenden ===== */
body:has(.malva){background:#F4F2EE;margin:0}
body:has(.malva) header.wp-block-template-part,
body:has(.malva) footer.wp-block-template-part,
body:has(.malva) .wp-block-post-title,
body:has(.malva) #wpadminbar~* .wp-block-post-title{display:none!important}
body:has(.malva) .wp-site-blocks,
body:has(.malva) main,
body:has(.malva) .wp-block-group:has(.malva),
body:has(.malva) .entry-content,
body:has(.malva) .wp-block-post-content{padding:0!important;margin:0!important;max-width:none!important;gap:0!important}
body:has(.malva) .has-global-padding{padding-left:0!important;padding-right:0!important}
.malva{max-width:none!important;width:100%!important;margin:0!important;font-family:"Outfit",system-ui,sans-serif;font-size:1.0625rem;line-height:1.6;color:#1F3D33;background:#F4F2EE}
.malva *,.malva *::before,.malva *::after{box-sizing:border-box}
.malva p,.malva ul,.malva ol,.malva h1,.malva h2,.malva h3,.malva figure,.malva blockquote{margin-block-start:0}
.malva a:where(:not(.btn)){text-decoration-thickness:1px}
.malva ul,.malva ol{padding-inline-start:0}
.malva .steps{padding-left:1.4rem}
.malva h1,.malva h2,.malva h3,.malva h4,.malva .h3,.malva button,.malva input,.malva select,.malva textarea,.malva summary,.malva blockquote{font-family:inherit;letter-spacing:inherit}
.malva h1,.malva h2,.malva h3{letter-spacing:-0.02em}
.malva .crisis ul{padding-left:1.2rem}
.malva .wp-block-image{margin:0!important;width:100%;height:100%}
.malva .wp-block-image img{display:block;width:100%;height:100%;object-fit:cover;border-radius:inherit}
.malva .photo .wp-block-image img{aspect-ratio:4/5;height:auto;border-radius:var(--radius)}
'''
full = '/* MALVA – einmal in WordPress unter "Zusätzliches CSS" einfügen */\n' + fonts + process(css) + '\n' + wp_fix
open(OUT + '/00-zusaetzliches-css.css', 'w').write(full)

# ---------- Seiten ----------
js_nav = open(ROOT + '/src/assets/js/nav.js').read().replace('document.documentElement.classList.add("js");', 'var w=document.querySelector(".malva");if(w)w.classList.add("js");')
js = {
    'nav': js_nav,
    'carousel': open(ROOT + '/src/assets/js/carousel.js').read(),
    'counter': open(ROOT + '/src/assets/js/counter.js').read(),
    'form': open(ROOT + '/src/assets/js/form-mailto.js').read(),
}

pages = {
    'startseite': 'index.html',
    'warum-malva': 'warum-malva/index.html',
    'team': 'team/index.html',
    'hilfe': 'hilfe/index.html',
    'partner': 'partner/index.html',
    'fachleute': 'fachleute/index.html',
    'spenden': 'spenden/index.html',
    'aktuelles': 'aktuelles/index.html',
    'kontakt': 'kontakt/index.html',
    'impressum': 'impressum/index.html',
    'datenschutz': 'datenschutz/index.html',
    'antrag-stellen': 'antrag-stellen/index.html',
}
imgs = set()
for i, (slug, path) in enumerate(pages.items(), 1):
    h = open(f'{ROOT}/_site/{path}').read()
    body = h.split('<body>', 1)[1].split('</body>', 1)[0]
    body = re.sub(r'<script[^>]*src="[^"]*"[^>]*></script>', '', body)
    for m in re.findall(r'/assets/img/([\w\-.]+)', body):
        imgs.add(m)
    body = body.replace('/assets/img/', UP)
    need = ['nav']
    if 'carousel' in body: need.append('carousel')
    if 'class="count"' in body: need.append('counter')
    if 'data-mailto' in body: need.append('form')
    scripts = ''.join(f'<script>\n{js[k]}\n</script>\n' for k in need)
    body = re.sub(r'\n\s*\n+', '\n', body).strip()
    out = f'<!-- MALVA · Seite „{slug}“ · in einen Block „Individuelles HTML“ einfügen -->\n<div class="malva">\n{body}\n</div>\n{scripts}'
    open(f'{OUT}/{i:02d}-{slug}.html', 'w').write(out)

print('Bilder:', sorted(imgs))
print('CSS kB:', round(len(full) / 1024))

const { chromium } = require('playwright');
const fs = require('fs');
(async () => {
  const dir = '/home/user/Lichtblick-app-android/malva-website/infoblatt/';
  const b = await chromium.launch();
  const p = await b.newPage({ viewport: { width: 794, height: 1123 }, deviceScaleFactor: 2 });
  await p.goto('file://' + dir + 'infoblatt-warum-jetzt.html');
  await p.evaluate(() => document.fonts.ready); await p.waitForTimeout(400);
  console.log(JSON.stringify(await p.evaluate(() => [...document.querySelectorAll('.page')].map(pg => [pg.scrollHeight, pg.clientHeight]))));
  const pages = await p.$$('.page');
  await pages[0].screenshot({ path: dir + 'malva-infoblatt-seite1.png' });
  if (pages[1]) await pages[1].screenshot({ path: dir + 'malva-infoblatt-seite2-brief.png' });
  const f = await p.evaluate(() => {
    const pg = document.querySelectorAll('.page')[1];
    if (!pg) return [];
    const top = pg.getBoundingClientRect().top;
    return [...document.querySelectorAll('[id^=f-]')].map(e => { const r = e.getBoundingClientRect(); return { id: e.id, x: r.left, y: r.top - top, w: r.width, h: r.height }; });
  });
  fs.writeFileSync('/home/user/shots/felder.json', JSON.stringify(f));
  await p.pdf({ path: '/home/user/shots/infoblatt-roh.pdf', width: '210mm', height: '297mm', printBackground: true });
  await b.close();
})();

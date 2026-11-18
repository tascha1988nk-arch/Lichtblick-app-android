// Zahlen zählen beim Hineinscrollen hoch. Ohne JavaScript oder bei
// „Bewegung reduzieren“ stehen sofort die echten Zahlen da.
(function () {
  var els = document.querySelectorAll(".count");
  if (!els.length || !("IntersectionObserver" in window)) return;
  if (window.matchMedia("(prefers-reduced-motion: reduce)").matches) return;
  var fmt = function (v, dec) { return v.toLocaleString("de-DE", { minimumFractionDigits: dec, maximumFractionDigits: dec }); };
  function run(el) {
    var to = parseFloat(el.dataset.to), dec = parseInt(el.dataset.dec || "0", 10), start = null, dur = 1600;
    function step(t) {
      if (!start) start = t;
      var p = Math.min((t - start) / dur, 1), e = 1 - Math.pow(1 - p, 3);
      el.textContent = fmt(to * e, dec);
      if (p < 1) requestAnimationFrame(step); else el.textContent = fmt(to, dec);
    }
    requestAnimationFrame(step);
  }
  var io = new IntersectionObserver(function (entries) {
    entries.forEach(function (en) { if (en.isIntersecting) { io.unobserve(en.target); run(en.target); } });
  }, { threshold: 0.6 });
  els.forEach(function (el) { el.textContent = fmt(0, parseInt(el.dataset.dec || "0", 10)); io.observe(el); });
})();

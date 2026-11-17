// Mobiles Menü auf- und zuklappen
(function () {
  var btn = document.querySelector(".nav-toggle");
  var nav = document.getElementById("hauptmenue");
  if (!btn || !nav) return;
  document.documentElement.classList.add("js");
  btn.addEventListener("click", function () {
    var open = btn.getAttribute("aria-expanded") === "true";
    btn.setAttribute("aria-expanded", String(!open));
    nav.classList.toggle("is-open", !open);
  });
  document.addEventListener("keydown", function (e) {
    if (e.key === "Escape" && nav.classList.contains("is-open")) {
      btn.setAttribute("aria-expanded", "false");
      nav.classList.remove("is-open");
      btn.focus();
    }
  });
})();

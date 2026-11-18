// Solange kein Formular-Dienst eingetragen ist, baut dieses Skript aus dem
// Formular eine gut lesbare E-Mail und öffnet das E-Mail-Programm.
(function () {
  var form = document.querySelector("form[data-mailto]");
  if (!form) return;
  form.addEventListener("submit", function (e) {
    if (!form.checkValidity()) return; // Browser zeigt die fehlenden Pflichtfelder
    e.preventDefault();
    var data = new FormData(form), lines = [], seen = {};
    data.forEach(function (value, key) {
      if (!String(value).trim()) return;
      if (seen[key] !== undefined) { lines[seen[key]] += ", " + value; return; }
      seen[key] = lines.length;
      lines.push(key + ": " + value);
    });
    var body = "Hallo Malva-Team,\n\nhier meine Anmeldung für das Netzwerk:\n\n" + lines.join("\n") + "\n";
    var subject = "Anmeldung Fachkolleg:innen – " + (data.get("Name") || "");
    window.location.href = "mailto:" + form.dataset.mailto +
      "?subject=" + encodeURIComponent(subject) + "&body=" + encodeURIComponent(body);
  });
})();

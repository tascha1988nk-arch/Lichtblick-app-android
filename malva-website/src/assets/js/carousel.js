// Karussell: Pfeile und Zähler nur, wenn nicht alle Karten sichtbar sind.
(function () {
  document.querySelectorAll(".carousel").forEach(function (root) {
    var track = root.querySelector(".carousel__track");
    var controls = root.querySelector(".carousel__controls");
    var prev = root.querySelector('[data-dir="-1"]');
    var next = root.querySelector('[data-dir="1"]');
    var count = root.querySelector(".carousel__count");
    var items = track.children;

    function step() { return items.length > 1 ? items[1].offsetLeft - items[0].offsetLeft : track.clientWidth; }
    function update() {
      var overflow = track.scrollWidth > track.clientWidth + 2;
      controls.hidden = !overflow;
      if (!overflow) return;
      var i = Math.round(track.scrollLeft / step());
      count.textContent = (i + 1) + " / " + items.length;
      prev.disabled = track.scrollLeft <= 2;
      next.disabled = track.scrollLeft + track.clientWidth >= track.scrollWidth - 2;
    }
    [prev, next].forEach(function (b) {
      b.addEventListener("click", function () { track.scrollBy({ left: step() * Number(b.dataset.dir) }); });
    });
    track.addEventListener("scroll", function () { window.requestAnimationFrame(update); }, { passive: true });
    window.addEventListener("resize", update);
    update();
  });
})();

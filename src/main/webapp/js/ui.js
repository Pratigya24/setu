(function () {
  // 1) Scroll-reveal: cards, panels, sections
  var sel = '.role-card,.stat-card,.item-card,.panel,.ngo-card,.story-card,.gallery-grid img,.auth-card,.form-wrapper,.cta-banner';
  var els = document.querySelectorAll(sel);
  var io = 'IntersectionObserver' in window ? new IntersectionObserver(function (entries) {
    entries.forEach(function (e) {
      if (e.isIntersecting) { e.target.classList.add('in'); io.unobserve(e.target); }
    });
  }, { threshold: 0.12 }) : null;
  els.forEach(function (el, i) {
    el.classList.add('reveal');
    el.style.transitionDelay = Math.min(i % 6, 5) * 70 + 'ms';
    io ? io.observe(el) : el.classList.add('in');
  });

  // 2) Count-up numbers (stats)
  document.querySelectorAll('.stat-card__value,.stats-strip__number').forEach(function (el) {
    var m = el.textContent.trim().match(/^(\d+)(\+?)$/);
    if (!m) return;
    var end = parseInt(m[1], 10), suffix = m[2], start = null;
    if (end === 0) return;
    function step(t) {
      start = start || t;
      var p = Math.min((t - start) / 1200, 1);
      el.textContent = Math.floor(end * (1 - Math.pow(1 - p, 3))) + suffix;
      if (p < 1) requestAnimationFrame(step);
    }
    requestAnimationFrame(step);
  });

  // 3) Password show/hide on every password field
  document.querySelectorAll('input[type=password]').forEach(function (inp) {
    var wrap = document.createElement('div');
    wrap.className = 'pw-wrap';
    inp.parentNode.insertBefore(wrap, inp);
    wrap.appendChild(inp);
    var b = document.createElement('button');
    b.type = 'button'; b.className = 'pw-toggle'; b.textContent = 'Show';
    b.onclick = function () {
      var show = inp.type === 'password';
      inp.type = show ? 'text' : 'password';
      b.textContent = show ? 'Hide' : 'Show';
    };
    wrap.appendChild(b);
  });

  // 4) Navbar shadow on scroll
  var nav = document.querySelector('.navbar');
  if (nav) window.addEventListener('scroll', function () {
    nav.classList.toggle('navbar--scrolled', window.scrollY > 8);
  }, { passive: true });

  // 5) Story modal
  var modal = document.getElementById('storyModal');
  if (modal) {
    document.querySelectorAll('.story-card').forEach(function (c) {
      c.addEventListener('click', function () {
        document.getElementById('smImg').src = c.dataset.img;
        document.getElementById('smTitle').textContent = c.dataset.title;
        document.getElementById('smContent').textContent = c.dataset.content;
        document.getElementById('smAuthor').textContent = c.dataset.author;
        modal.classList.add('open');
      });
    });
    modal.addEventListener('click', function (e) {
      if (e.target === modal || e.target.classList.contains('story-modal__close')) modal.classList.remove('open');
    });
    document.addEventListener('keydown', function (e) { if (e.key === 'Escape') modal.classList.remove('open'); });
  }
})();
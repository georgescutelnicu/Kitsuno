document.addEventListener('DOMContentLoaded', function () {
    const box = document.getElementById('stroke-order');
    if (!box || !box.dataset.src) return;

    const reduceMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;

    box.setAttribute('role', 'button');
    box.setAttribute('tabindex', '0');

    function colorStrokes() {
        box.querySelectorAll('svg path').forEach((p, i) => {
            const hue = (i * 137.5) % 360;
            p.style.fill = 'none';
            p.style.strokeWidth = '3';
            p.style.strokeLinecap = 'round';
            p.style.strokeLinejoin = 'round';
            p.style.stroke = `hsl(${hue}, 85%, 45%)`;
        });
    }

    function animateStrokes() {
        let delay = 0;

        box.querySelectorAll('svg path').forEach(p => {
            const len = p.getTotalLength();

            p.style.transition = 'none';
            p.style.strokeDasharray = len;
            p.style.strokeDashoffset = len;
            p.getBoundingClientRect();

            const duration = Math.max(0.4, len / 150);
            p.style.transition = `stroke-dashoffset ${duration}s ease-in-out ${delay}s`;
            p.style.strokeDashoffset = 0;

            delay += duration + 0.1;
        });
    }

    function replay() {
        if (!reduceMotion && box.querySelector('svg')) animateStrokes();
    }

    fetch(box.dataset.src)
        .then(r => r.text())
        .then(text => {
            const start = text.indexOf('<svg');
            const end = text.lastIndexOf('</svg>') + '</svg>'.length;
            box.innerHTML = text.slice(start, end);
            colorStrokes();
            if (box.hasAttribute('data-autoplay')) replay();
        })
        .catch(err => console.error('Could not load stroke order SVG', err));

    box.addEventListener('click', replay);
    box.addEventListener('keydown', e => {
        if (e.key === 'Enter' || e.key === ' ') {
            e.preventDefault();
            replay();
        }
    });
});
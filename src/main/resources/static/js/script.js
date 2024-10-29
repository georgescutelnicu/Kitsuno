// Redirect li items to their respective anchor tag link
const navItems = document.querySelectorAll(".nav-links li");

navItems.forEach(item => {
    item.addEventListener("click", () => {
        const link = item.querySelector("a").getAttribute("href");
            window.location.href = link;
    });
});


// Navbar user tab dropdown
document.addEventListener('DOMContentLoaded', function () {
    const userLink = document.querySelector('.user-link');
    const userDropdown = document.querySelector('.user-dropdown');

    userLink.addEventListener('click', function (event) {
        event.preventDefault();
        userLink.classList.toggle('toggle');
        userDropdown.style.display = userLink.classList.contains('toggle') ? 'block' : 'none';
    });

    // Close dropdown when clicking outside
    window.addEventListener('click', function(event) {
        if (!userLink.contains(event.target)) {
            userLink.classList.remove('toggle');
            userDropdown.style.display = 'none';
        }
    });
});


// Dynamically creates a modal window displaying detailed information about a kana character
function showInfo(character, romaji, audio, story, mnemonic, strokeOrder) {

    const modalOverlay = document.createElement('div');
    modalOverlay.className = 'modal';

    const modalContent = document.createElement('div');
    modalContent.className = 'modal-content';
    modalContent.innerHTML = `
            <h2>${character} (${romaji})</h2>
            <button class="audio-button" data-audio="${audio}" onclick="playAudio(this.dataset.audio)">🔊</button>
            <div class="flip-container">
                <div class="flipper">
                    <div class="front">
                        <img src="${mnemonic}" alt="Mnemonic for ${character}">
                        <p class="mnemonic-text">${story}</p>
                    </div>
                    <div class="back">
                        <img src="${strokeOrder}" alt="Stroke Order for ${character}">
                    </div>
                </div>
            </div>`;

    modalOverlay.appendChild(modalContent);

    document.body.appendChild(modalOverlay);
    modalOverlay.style.display = 'flex';

    requestAnimationFrame(() => {
        modalContent.style.transform = 'scale(1)';
    });

    modalOverlay.addEventListener('click', function (e) {
        if (e.target === modalOverlay) {
            modalContent.style.transform = 'scale(0)';
            setTimeout(() => {
                document.body.removeChild(modalOverlay);
            }, 300);
        }
    });
}


// Play audio in kana pages
function playAudio(audioSource) {
    const audio = new Audio(audioSource);
    audio.play();
}


// Show/hide flashcard form
function showFlashcardForm() {
    document.getElementById("flashcard-form").classList.remove("hidden");
    document.querySelector(".flashcard-button-form").style.display = "none";
}

function hideFlashcardForm() {
    document.getElementById("flashcard-form").classList.add("hidden");
    document.querySelector(".flashcard-button-form").style.display = "block";
}

function flashcardError() {
    document.getElementById("flashcard-error").classList.remove("hidden");
}
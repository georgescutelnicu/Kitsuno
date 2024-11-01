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


// Handle hiragana typing in vocabulary forms
const romajiToHiragana = {
    'a': 'あ', 'i': 'い', 'u': 'う', 'e': 'え', 'o': 'お',
    'ka': 'か', 'ki': 'き', 'ku': 'く', 'ke': 'け', 'ko': 'こ',
    'ga': 'が', 'gi': 'ぎ', 'gu': 'ぐ', 'ge': 'げ', 'go': 'ご',
    'sa': 'さ', 'shi': 'し', 'su': 'す', 'se': 'せ', 'so': 'そ',
    'za': 'ざ', 'ji': 'じ', 'zu': 'ず', 'ze': 'ぜ', 'zo': 'ぞ',
    'ta': 'た', 'chi': 'ち', 'tsu': 'つ', 'te': 'て', 'to': 'と',
    'da': 'だ', 'ji': 'ぢ', 'zu': 'づ', 'de': 'で', 'do': 'ど',
    'na': 'な', 'ni': 'に', 'nu': 'ぬ', 'ne': 'ね', 'no': 'の',
    'ha': 'は', 'hi': 'ひ', 'fu': 'ふ', 'he': 'へ', 'ho': 'ほ',
    'ba': 'ば', 'bi': 'び', 'bu': 'ぶ', 'be': 'べ', 'bo': 'ぼ',
    'pa': 'ぱ', 'pi': 'ぴ', 'pu': 'ぷ', 'pe': 'ぺ', 'po': 'ぽ',
    'ma': 'ま', 'mi': 'み', 'mu': 'む', 'me': 'め', 'mo': 'も',
    'ya': 'や', 'yu': 'ゆ', 'yo': 'よ',
    'ra': 'ら', 'ri': 'り', 'ru': 'る', 're': 'れ', 'ro': 'ろ',
    'wa': 'わ', 'wo': 'を', 'n': 'ん',

    'kya': 'きゃ', 'kyu': 'きゅ', 'kyo': 'きょ',
    'gya': 'ぎゃ', 'gyu': 'ぎゅ', 'gyo': 'ぎょ',
    'sha': 'しゃ', 'shu': 'しゅ', 'sho': 'しょ',
    'cha': 'ちゃ', 'chu': 'ちゅ', 'cho': 'ちょ',
    'nya': 'にゃ', 'nyu': 'にゅ', 'nyo': 'にょ',
    'hya': 'ひゃ', 'hyu': 'ひゅ', 'hyo': 'ひょ',
    'bya': 'びゃ', 'byu': 'びゅ', 'byo': 'びょ',
    'pya': 'ぴゃ', 'pyu': 'ぴゅ', 'pyo': 'ぴょ',
    'mya': 'みゃ', 'myu': 'みゅ', 'myo': 'みょ',
    'rya': 'りゃ', 'ryu': 'りゅ', 'ryo': 'りょ',
};

function convertToHiragana(inputElement) {
    let value = inputElement.value;
    let convertedText = '';
    let tempRomaji = '';

    for (let i = 0; i < value.length; i++) {
        const char = value[i].toLowerCase();

        if (char === 'n') {
            tempRomaji += 'n';
        } else {
            if (tempRomaji === 'n') {
                if ('aiueo'.includes(char)) {
                    convertedText += romajiToHiragana[tempRomaji + char];
                    tempRomaji = '';
                    continue;
                } else {
                    convertedText += 'ん';
                    tempRomaji = '';
                }
            }

            tempRomaji += char;

            if (romajiToHiragana[tempRomaji]) {
                convertedText += romajiToHiragana[tempRomaji];
                tempRomaji = '';
            } else if (tempRomaji.length === 1 && 'aiueo'.includes(tempRomaji)) {
                convertedText += romajiToHiragana[tempRomaji];
                tempRomaji = '';
            } else if (tempRomaji.length > 1 && tempRomaji[0] === tempRomaji[1]) {
                convertedText += 'っ';
                tempRomaji = tempRomaji.slice(1);
            } else if (tempRomaji.length > 3 || !Object.keys(romajiToHiragana).some(key => key.startsWith(tempRomaji))) {

                convertedText += tempRomaji[0];
                tempRomaji = tempRomaji.slice(1);
            }
        }
    }

    convertedText += tempRomaji;
    inputElement.value = convertedText;
}


// Highlights text in red when flashcard inputs character count exceeds the specified limit.
document.addEventListener("DOMContentLoaded", function () {
    const limitElements = document.querySelectorAll(".character-limit");

    limitElements.forEach(element => {
        const limit = parseInt(element.getAttribute("data-limit"));

        element.addEventListener("input", function () {
            if (element.value.length > limit) {
                element.style.color = "red";
            } else {
                element.style.color = "black";
            }
        });
    });
});


// Toggle down resources
function toggleDropdown(id) {
    const content = document.getElementById(id);
    const header = content.previousElementSibling;

    if (content.style.maxHeight) {
        content.style.maxHeight = null;
        header.classList.remove("active-rss");
    } else {
        content.style.maxHeight = content.scrollHeight + "px";
        header.classList.add("active-rss");
    }
}

const dropdowns = document.querySelectorAll('.dropdown-content ul li a');
dropdowns.forEach(link => {
    link.addEventListener('click', (event) => {
        event.stopPropagation();
    });
});
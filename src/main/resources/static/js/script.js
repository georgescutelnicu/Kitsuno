// Redirect li items to their respective anchor tag link
const navItems = document.querySelectorAll(".nav-links li");

navItems.forEach(item => {
    item.addEventListener("click", () => {
        const link = item.querySelector("a").getAttribute("href");
            window.location.href = link;
    });
});


// Adjust font size of the username displayed on the top navbar
function adjustFontSize() {
    const usernameDisplay = document.querySelector('.user-section h6');
    const fullText = usernameDisplay.textContent;
    const username = fullText.split(' ')[1];

    let fontSize = 24;

    if (username.length > 7 && username.length <= 10) {
        fontSize = 20;
    } else if (username.length > 10) {
        fontSize = 16;
    }

    usernameDisplay.style.fontSize = `${fontSize}px`;
}

window.addEventListener('DOMContentLoaded', adjustFontSize);


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
let currentModal = null;

function showInfo(id) {
    if (!currentModal) {
        const modalOverlay = document.createElement('div');
        modalOverlay.className = 'modal';

        const modalContent = document.createElement('div');
        modalContent.className = 'modal-content';

        modalOverlay.appendChild(modalContent);
        document.body.appendChild(modalOverlay);

        modalOverlay.style.display = 'flex';
        currentModal = modalOverlay;
    }

    const kanaData = getKanaDataById(id);

    const modalContent = currentModal.querySelector('.modal-content');
    modalContent.innerHTML = `
        <div class="modal-header">
            <button class="navigate-button" onclick="navigateKana(${id}, 'back')">←</button>
            <h2>${kanaData.character} (${kanaData.romaji})</h2>
            <button class="navigate-button" onclick="navigateKana(${id}, 'next')">→</button>
        </div>
        <button class="audio-button" data-audio="${kanaData.audio}" onclick="playAudio(this.dataset.audio)">🔊</button>
        <div class="flip-container">
            <div class="flipper">
                <div class="front">
                    <img src="${kanaData.mnemonic}" alt="Mnemonic for ${kanaData.character}">
                    <p class="mnemonic-text">${kanaData.story}</p>
                </div>
                <div class="back">
                    <img src="${kanaData.strokeOrder}" alt="Stroke Order for ${kanaData.character}">
                </div>
            </div>
        </div>


    `;

    requestAnimationFrame(() => {
        modalContent.style.transform = 'scale(1)';
    });

    currentModal.addEventListener('click', function (e) {
        if (e.target === currentModal) {
            modalContent.style.transform = 'scale(0)';
            setTimeout(() => {
                document.body.removeChild(currentModal);
                currentModal = null;
            }, 300);
        }
    });
}

function getKanaDataById(id) {
    return kanaList.find(kana => kana.id === id);
}

function navigateKana(currentId, direction) {
    const currentIndex = kanaList.findIndex(kana => kana.id === currentId);
    let newIndex = currentIndex;

    if (direction === 'next') {
        do {
            newIndex = (newIndex + 1) % kanaList.length;
        } while (kanaList[newIndex] === null || kanaList[newIndex].romaji === null);
    } else if (direction === 'back') {
        do {
            newIndex = (newIndex - 1 + kanaList.length) % kanaList.length;
        } while (kanaList[newIndex] === null || kanaList[newIndex].romaji === null);
    }

    showInfo(kanaList[newIndex].id);
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


// Go back to the last page
function goBack() {
    if (document.referrer && document.referrer.includes("/vocabulary")) {
        window.location.href = "/vocabulary";
    } else if (window.history.length > 1) {
        window.history.back();
    } else {
        window.location.href = "/vocabulary";
    }
}


// Disables export buttons and shows a message during flashcard export
document.querySelectorAll('.export-form').forEach(form => {
    form.addEventListener('submit', function(event) {
        const buttons = document.querySelectorAll('.export-button');
        const loadingMessage = document.getElementById('loadingMessage');

        loadingMessage.style.display = 'block';

        buttons.forEach(button => {
            button.disabled = true;
            button.classList.add('inactive');
        });

        setTimeout(() => {
            loadingMessage.style.display = 'none';

            buttons.forEach(button => {
                button.disabled = false;
                button.classList.remove('inactive');
            });
        }, 8000);
    });
});


// Reveal Api Key
document.getElementById('apiKey').addEventListener('click', function() {
    var apiKeyElement = document.getElementById('apiKey');
    apiKeyElement.classList.toggle('revealed');
});
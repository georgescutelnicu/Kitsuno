# Kitsuno

<p align="center">
  <img src="extras/banner_repo.png" alt="Kitsuno Banner">
</p>

Kitsuno is a free platform focused on helping you learn hiragana, katakana, and kanji. With features like customizable flashcards and engaging practice games, Kitsuno makes it easy to study and retain Japanese characters at your own pace. Whether you're just starting or looking to improve, Kitsuno offers interactive tools to enhance your kana and kanji learning experience.

## Explore
Explore Kitsuno and its features through the links below:
<br><br>
<a href="https://kitsuno.onrender.com/v1/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/API Docs-85EA2D?logo=swagger&logoColor=000&style=for-the-badge"></img>
</a>
<br>
<a href="https://kitsuno.onrender.com/">
    <img src="https://img.shields.io/badge/Kitsuno-000?logo=render&logoColor=fff&style=for-the-badge"></img>
</a>
<br><br>
Please note that it may take up to 50 seconds to start when first accessed, as I am using a free instance on Render.<br>
This is due to the instance spinning down after a period of inactivity. Once it's warmed up, the loading times will be faster.
<br><br>
*Hosted with [Render](https://render.com/)*

## About

Kitsuno offers a range of useful features to help you learn and practice Japanese effectively:

- **Learn Kana and Kanji**: Focused resources to help you master hiragana, katakana, and kanji characters.
- **Customizable Flashcards**: Create your own flashcards to focus on specific characters or vocabulary.
- **Flashcard Export**: Export flashcards to CSV or Anki decks for flexible studying.
- **Interactive Practice Games**: Engage in fun and effective games to test your knowledge and reinforce learning.
- **Kitsuno API**: Access endpoints for kana, kanji, and flashcards to enhance your personalized learning experience.
- **Jotoba API Integration**: Discover real-world sentences and examples that help you understand word usage in context.
- **Completely Free**: All features, tools, and resources are free to use with no hidden costs.

## Technologies Used

**Front-End Development:** HTML, CSS, JS, Thymeleaf.<br>
**Back-End Development:** Java (Spring Boot), PostgreSQL, Python (genanki).<br>
**Containerization:** Docker.<br>
**Deployment:** Render.<br>
**API Docs:** SpringDoc OpenAPI.<br>
**API Integration:** Jotoba API.  <br>
**Testing:** JUnit5.

# Kitsuno API

## Authorization

All API requests require the use of a generated API key. You can find your API key or generate a new one in your profile tab after registering.  
To authenticate an API request, you should provide your API key in the API-KEY header.

| Headers       | Parameter    | Description                                        |
| ------------- | ------------ | -------------------------------------------------- |
| API-KEY | api_key   | **Required**. Your Kitsuno API key.               |

## Request Methods

| Method   | Description                                                              |
| -------- | ------------------------------------------------------------------------ |
| GET    | Used to retrieve a single item or a collection of items.                 |
| POST   | Used when creating new items.                                            |
| PUT    | Used to update one or more fields on an item.                             |
| DELETE | Used to delete an item.                                                  |

## Endpoints

| Method   | URL                                           | Description                                              | Parameters                                             |
| -------- | --------------------------------------------- | -------------------------------------------------------- | ---------------------------------------------------------------------------- |
| GET    | /api/hiragana                               | Retrieve a list of all Hiragana characters.               | - / -                                                                         |
| GET    | /api/hiragana/{romaji}                      | Retrieve a specific Hiragana character by romaji.         | romaji (Hiragana romaji character)                                          |
| GET    | /api/katakana                               | Retrieve a list of all Katakana characters.               | - / -                                                                         |
| GET    | /api/katakana/{romaji}                      | Retrieve a specific Katakana character by romaji.         | romaji (Katakana romaji character)                                          |
| GET    | /api/kanji                                  | Retrieve a list of all Kanji characters.                  | - / -                                                                         |
| GET    | /api/kanji/{id}                             | Retrieve a specific Kanji character by ID.                | id (Kanji character ID)                                                    |
| GET    | /api/flashcards                             | Retrieve all your flashcards.                       | - / -                                                                         |
| GET    | /api/flashcards/{flashcardId}                | Retrieve one of your flashcards by ID.                               | flashcardId (ID of the flashcard)                                          |
| POST   | /api/flashcards                             | Create a new flashcard.                        | Flashcard data (check API Docs)                          |
| PUT    | /api/flashcards/{flashcardId}                | Update an existing flashcard by ID.                       | flashcardId (ID of the flashcard), Updated flashcard data (check API Docs)                 |
| DELETE | /api/flashcards/{flashcardId}                | Delete a flashcard by ID.                                 | flashcardId (ID of the flashcard to delete)                                 |

## HTTP Response Status Codes

| Code  | Title                     | Description                                                       |
| ----- | ------------------------- | ----------------------------------------------------------------- |
| 200 | OK                      | Successful request.                                               |
| 201 | Created                 | Successfully created a new resource.                              |
| 400 | Bad Request             | Invalid request or request parameters.                            |
| 401 | Unauthorized            | Unauthorized access. API key is missing or invalid.              |
| 404 | Not Found               | Resource not found.                                               |
| 500 | Internal Server Error   | Internal server error.                                           |

## Examples
Kana(hiragana & katakana) response format:<br>

```
{
  "id": 1,
  "character": "あ",
  "romaji": "a",
  "audio": "https://files.tofugu.com/articles/japanese/2014-06-30-learn-hiragana/あ.mp3",
  "mnemonic": "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/01-A.png/500px-01-A.png",
  "story": "あ is an Astronaut.",
  "strokeOrder": "https://upload.wikimedia.org/wikipedia/commons/d/d8/Hiragana_%E3%81%82_stroke_order_animation.gif?20100306181611"
}
```

<br>Kanji response format:<br>

```
{
  "id": 2,
  "character": "日",
  "jlpt": "N5",
  "meanings": "day, sun, Japan, counter for days",
  "onyomiReadings": [
    "ニチ",
    "ジツ"
  ],
  "kunyomiReadings": [
    "ひ",
    "-び",
    "-か"
  ],
  "onyomiVocab": [
    "毎日 【まいにち】every day",
    "日光 【にっこう】sunlight",
    "翌日 【よくじつ】next day"
  ],
  "kunyomiVocab": [
    "日 【ひ】day,  days",
    "記念日 【きねんび】anniversary,  memorial day"
  ],
  "category": "Time",
  "strokeCount": 4
}
```

<br>Flashcard response format:<br>

```
{
  "id": 4,
  "kanjiCharacter": "日",
  "kanjiJlpt": "N5",
  "kanjiMeanings": "day, sun, Japan, counter for days",
  "onyomiReadings": [
    "ニチ",
    "ジツ"
  ],
  "kunyomiReadings": [
    "ひ",
    "-び",
    "-か"
  ],
  "vocabulary": [
    "まいにち every day",
    "にほんご japanese language",
    "いちにちじゅう all day long"
  ],
  "notes": "Whenever you see 日, remember this window with the sun shining through, starting a brand-new day."
}
```

---
## Credits

- Kana details scraped from [Wikibooks](https://en.wikibooks.org/wiki/Memorizing_the_Hiragana) and [Wikimedia](https://commons.wikimedia.org/wiki/Hiragana).
- Kana audio pronunciation gathered from [Tofugu](https://www.tofugu.com/japanese/learn-hiragana/).
- Kanji details sourced from [JLPT Sensei](https://jlptsensei.com/jlpt-n5-kanji-list/).
- Images collected from Freepik: [catalyststuff](https://www.freepik.com/author/catalyststuff) and [macrovector](https://www.freepik.com/author/macrovector).
- Icons used from [Lineicons](https://lineicons.com/).
- [Jotoba API](https://jotoba.de/docs.html) to retrieve vocabulary/sentences by word.
- [Swagger UI](https://swagger.io/tools/swagger-ui/) for Kitsuno API documentation.

## License

This project is licensed under the MIT License.

<br>

*Master the building blocks of japanese with Kitsuno, one character at a time!*

---

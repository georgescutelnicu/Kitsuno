ALTER TABLE kanji ALTER COLUMN id RESTART WITH 1;

INSERT INTO kanji (character, jlpt, meanings, onyomi_readings, kunyomi_readings, onyomi_vocab, kunyomi_vocab, category, stroke_count)
VALUES
('日', 'N5', '["day", "sun", "Japan", "counter for days"]', '["ニチ", "ジツ"]', '["ひ", "-び", "-か"]',
 '["毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"]',
 '["日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"]', 'Time', 4),
('人', 'N5', '["person"]', '["ジン", "ニン"]', '["ひと"]',
 '["~人 【じん】 often used for citizenship after a country (American, Chinese, etc..)", "友人 【ゆうじん】friend"]',
 '["人 【ひと】 man, person, people", "いい人 【いいひと】 good-natured person, good person, lover"]', 'People', 2);


ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

INSERT INTO users (username, email, password, enabled, join_date)
VALUES
    ('user1', 'user1@example.com', 'hashedpassword1', true, CURRENT_DATE),
    ('user2', 'user2@example.com', 'hashedpassword2', true, CURRENT_DATE);


ALTER TABLE flashcards ALTER COLUMN id RESTART WITH 1;

INSERT INTO flashcards (vocabulary, notes, kanji_id, user_id)
VALUES
(ARRAY['ひあさ morning sun'], 'Flashcard note for 日', 1, 1),
(ARRAY['ひとびと people'], 'Flashcard note for 人', 2, 1),
(ARRAY['ひさし long time'], 'Flashcard note for 日', 1, 2);
